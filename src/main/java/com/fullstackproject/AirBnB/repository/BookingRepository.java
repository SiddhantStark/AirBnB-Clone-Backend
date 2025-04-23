package com.fullstackproject.AirBnB.repository;

import com.fullstackproject.AirBnB.dto.BookingDto;
import com.fullstackproject.AirBnB.entity.Booking;
import com.fullstackproject.AirBnB.entity.Hotel;
import com.fullstackproject.AirBnB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByPaymentSessionId(String sessionId);
    List<Booking> findByHotel(Hotel hotel);
    List<Booking> findByHotelAndCreatedAtBetween(Hotel hotel, LocalDateTime startTime, LocalDateTime endTime);
    List<Booking> getByUser(User user);
}
