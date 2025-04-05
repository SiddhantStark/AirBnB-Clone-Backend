package com.fullstackproject.AirBnB.controller;

import com.fullstackproject.AirBnB.dto.BookingDto;
import com.fullstackproject.AirBnB.dto.BookingRequest;
import com.fullstackproject.AirBnB.dto.GuestDto;
import com.fullstackproject.AirBnB.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

    private final BookingService bookingService;
    @PostMapping("/init")
    public ResponseEntity<BookingDto> initializeBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(bookingService.initalizeBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId, @RequestBody List<GuestDto> guestDtoList){
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDtoList));
    }
}
