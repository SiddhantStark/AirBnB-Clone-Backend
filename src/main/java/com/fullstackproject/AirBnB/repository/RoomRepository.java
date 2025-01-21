package com.fullstackproject.AirBnB.repository;

import com.fullstackproject.AirBnB.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
