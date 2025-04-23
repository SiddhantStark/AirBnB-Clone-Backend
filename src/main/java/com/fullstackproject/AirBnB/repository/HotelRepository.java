package com.fullstackproject.AirBnB.repository;

import com.fullstackproject.AirBnB.dto.HotelDto;
import com.fullstackproject.AirBnB.entity.Hotel;
import com.fullstackproject.AirBnB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByOwner(User user);
}
