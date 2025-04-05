package com.fullstackproject.AirBnB.repository;

import com.fullstackproject.AirBnB.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
