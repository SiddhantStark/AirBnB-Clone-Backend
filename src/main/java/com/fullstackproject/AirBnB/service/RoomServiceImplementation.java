package com.fullstackproject.AirBnB.service;

import com.fullstackproject.AirBnB.dto.HotelDto;
import com.fullstackproject.AirBnB.dto.RoomDto;
import com.fullstackproject.AirBnB.entity.Hotel;
import com.fullstackproject.AirBnB.entity.Room;
import com.fullstackproject.AirBnB.entity.User;
import com.fullstackproject.AirBnB.exception.ResourceNotFoundException;
import com.fullstackproject.AirBnB.exception.UnauthorisedException;
import com.fullstackproject.AirBnB.repository.HotelRepository;
import com.fullstackproject.AirBnB.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImplementation implements RoomService{
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final PricingUpdateService pricingUpdateService;
    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating a new room in Hotel with Id: " + hotelId);
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(user.equals(hotel.getOwner())){
            throw new UnauthorisedException("This user does not own this hotel with ID: " + hotelId);
        }

        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

//        TODO: create inventory as soon as room is created and if hotel is active
        //        if (hotel.getActive()) {
//            inventoryService.initializeRoomForAYear(room);
//        }
        inventoryService.initializeRoomForAYear(room);
        pricingUpdateService.updateHotelPrices(hotel);

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all rooms in Hotel with Id: " + hotelId);
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnauthorisedException("This user does not own this hotel with ID: " + hotelId);
        }
        return hotel.getRooms().stream().map((element) -> modelMapper.map(element, RoomDto.class)).collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting the room with Id: {}" , roomId);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + roomId));
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        log.info("Deleting the room with Id: {}" , roomId);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + roomId));
//        Todo: delete for future inventory for this room
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(room.getHotel().getOwner())){
            throw new UnauthorisedException("This user does not own this room with ID: " + roomId);
        }
        inventoryService.deleteAllInventories(room);

        roomRepository.deleteById(roomId);
    }

    @Override
    @Transactional
    public RoomDto updateRoomByID(Long hotelId, RoomDto roomDto, Long roomId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnauthorisedException("This user does not own this hotel with ID: " + roomId);
        }

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
        modelMapper.map(roomDto, room);
        room.setId(roomId);
        room.setHotel(hotel);

//        If Price or Inventory is updated, then update the inventory for this room
        room = roomRepository.save(room);
        return modelMapper.map(room, RoomDto.class);
    }
}
