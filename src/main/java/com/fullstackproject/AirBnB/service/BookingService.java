package com.fullstackproject.AirBnB.service;

import com.fullstackproject.AirBnB.dto.BookingDto;
import com.fullstackproject.AirBnB.dto.BookingRequest;
import com.fullstackproject.AirBnB.dto.GuestDto;

import java.util.List;

public interface BookingService {
    BookingDto initalizeBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
