package com.fullstackproject.AirBnB.service;

import com.fullstackproject.AirBnB.dto.HotelDto;
import com.fullstackproject.AirBnB.dto.HotelSearchRequest;
import com.fullstackproject.AirBnB.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializeRoomForAYear(Room room);
    void deleteAllInventories(Room room);
    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
