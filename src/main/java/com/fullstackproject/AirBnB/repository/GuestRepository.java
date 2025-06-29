package com.fullstackproject.AirBnB.repository;

import com.fullstackproject.AirBnB.entity.Guest;
import com.fullstackproject.AirBnB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findByUser(User user);
}
