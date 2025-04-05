package com.fullstackproject.AirBnB.repository;

import com.fullstackproject.AirBnB.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
