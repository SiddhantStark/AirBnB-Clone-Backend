package com.fullstackproject.AirBnB.service;

import com.fullstackproject.AirBnB.dto.BookingDto;
import com.fullstackproject.AirBnB.dto.BookingRequest;
import com.fullstackproject.AirBnB.dto.GuestDto;
import com.fullstackproject.AirBnB.entity.*;
import com.fullstackproject.AirBnB.entity.enums.BookingStatus;
import com.fullstackproject.AirBnB.exception.ResourceNotFoundException;
import com.fullstackproject.AirBnB.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImplementation implements BookingService{

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;

    @Override
    @Transactional
    public BookingDto initalizeBooking(BookingRequest bookingRequest) {
        log.info("Initializing booking for hotel : {}, room: {}, date: {}-{}", bookingRequest.getHotelId(), bookingRequest.getRoomId(), bookingRequest.getCheckInDate(),  bookingRequest.getCheckOutDate());
        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: {}" + bookingRequest.getHotelId()));
        Room room = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: {}" + bookingRequest.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(),bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), bookingRequest.getRoomsCount());
        long dayCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate()) + 1;

        if(inventoryList.size() < dayCount){
            throw new IllegalStateException("Room is not available anymore");
        }

//        Reserve the room,  and update the booked count in inventories

        for(Inventory inventory: inventoryList){
            inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);

        User user = new User();
        user.setId(1L);

        Booking booking = Booking.builder().bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .user(user)
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {
        log.info("Adding guests for booking with ID: {}", bookingId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        if(isBookingExpired(booking)){
            throw new IllegalStateException("Booking has already expired");
        }

        if(booking.getBookingStatus() != BookingStatus.RESERVED){
            throw new IllegalStateException("Booking is not under reserved state, cannot add guests!");
        }

        for(GuestDto guestDto: guestDtoList){
            Guest guest = modelMapper.map(guestDto, Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }

        booking.setBookingStatus(BookingStatus.GUEST_ADDED);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    public boolean isBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser(){
        User user = new User();
        user.setId(1L);
        return user;
    }
}