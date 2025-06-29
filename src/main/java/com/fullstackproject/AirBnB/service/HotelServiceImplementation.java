package com.fullstackproject.AirBnB.service;

import com.fullstackproject.AirBnB.dto.*;
import com.fullstackproject.AirBnB.entity.Hotel;
import com.fullstackproject.AirBnB.entity.Room;
import com.fullstackproject.AirBnB.entity.User;
import com.fullstackproject.AirBnB.exception.ResourceNotFoundException;
import com.fullstackproject.AirBnB.exception.UnauthorisedException;
import com.fullstackproject.AirBnB.repository.HotelRepository;
import com.fullstackproject.AirBnB.repository.InventoryRepository;
import com.fullstackproject.AirBnB.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.fullstackproject.AirBnB.util.AppUtils.getCurrentUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImplementation implements HotelService{
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;
    private final PricingUpdateService pricingUpdateService;
    private final InventoryRepository inventoryRepository;
    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting hotel with id: {}", id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())){
            throw new UnauthorisedException("This user does not own this hotel with ID: " + id);
        }
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));
        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnauthorisedException("This user does not own this hotel with ID: " + id);
        }
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));
        hotelRepository.deleteById(id);
//        delete the future inventories for this hotel

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnauthorisedException("This user does not own this hotel with ID: " + id);
        }

        for(Room room: hotel.getRooms()) {
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
    }

    @Override
    @Transactional
    public void activateHotel(Long id) {
        log.info("Activating the hotel with Id" + id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
//        create inventories for all the rooms for this hotel

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Owner of hotel: " + user.getId());
        log.info("id of user: " + hotel.getOwner().getId());
        if(!user.equals(hotel.getOwner())){
            throw new UnauthorisedException("This user does not own this hotel with ID: " + id);
        }

        for(Room room: hotel.getRooms()) {
            inventoryService.initializeRoomForAYear(room);
        }

        hotel.setActive(true);
        hotelRepository.save(hotel);
        pricingUpdateService.updateHotelPrices(hotel);
    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId, HotelInfoRequestDto hotelInfoRequestDto) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        long daysCount = ChronoUnit.DAYS.between(hotelInfoRequestDto.getStartDate(), hotelInfoRequestDto.getEndDate())+1;

        List<RoomPriceDto> roomPriceDtoList = inventoryRepository.findRoomAveragePrice(hotelId,
                hotelInfoRequestDto.getStartDate(), hotelInfoRequestDto.getEndDate(),
                hotelInfoRequestDto.getRoomsCount(), daysCount);

        List<RoomPriceResponseDto> rooms = roomPriceDtoList.stream()
                .map(roomPriceDto -> {
                    RoomPriceResponseDto roomPriceResponseDto = modelMapper.map(roomPriceDto.getRoom(),
                            RoomPriceResponseDto.class);
                    roomPriceResponseDto.setPrice(roomPriceDto.getPrice());
                    return roomPriceResponseDto;
                })
                .collect(Collectors.toList());

        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
    }

    @Override
    public List<HotelDto> getAllHotels() {
        User user = getCurrentUser();
        log.info("Getting all hotels for admin user with ID: {} ", user.getId());
        List<Hotel> hotels = hotelRepository.findByOwner(user);
        return hotels.stream().map((element) -> modelMapper.map(element, HotelDto.class)).collect(Collectors.toList());
    }

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating new hotel with name: {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        hotel.setOwner(user);
        hotel = hotelRepository.save(hotel);
        log.info("Created new hotel with id: {}", hotel.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

}
