package com.fullstackproject.AirBnB.service;

import com.fullstackproject.AirBnB.dto.BookingDto;
import com.fullstackproject.AirBnB.dto.BookingRequest;
import com.fullstackproject.AirBnB.dto.GuestDto;
import com.fullstackproject.AirBnB.dto.HotelReportDto;
import com.stripe.model.Event;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingService {
    BookingDto initalizeBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);

    String initiatePayments(Long bookingId);

    void capturePayment(Event event);

    void cancelPayments(Long bookingId);

    String getBookingStatus(Long bookingId);

    List<BookingDto> getAllBookingsByHotelId(Long hotelID) throws AccessDeniedException;

    HotelReportDto getHotelReport(Long hotelID, LocalDate startDate, LocalDate endDate) throws AccessDeniedException;

    List<BookingDto> getMyBookings();
}
