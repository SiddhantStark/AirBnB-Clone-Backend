package com.fullstackproject.AirBnB.service;

import com.fullstackproject.AirBnB.dto.HotelPriceDto;
import com.fullstackproject.AirBnB.dto.HotelSearchRequest;
import com.fullstackproject.AirBnB.dto.InventoryDto;
import com.fullstackproject.AirBnB.dto.UpdateInventoryRequestDto;
import com.fullstackproject.AirBnB.entity.Room;
import org.springframework.data.domain.Page;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface InventoryService {
    void initializeRoomForAYear(Room room);
    void deleteAllInventories(Room room);
    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);

    List<InventoryDto> getAllInventoriesByRoom(Long roomId) throws AccessDeniedException;
    void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto) throws AccessDeniedException;
}
