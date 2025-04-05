package com.fullstackproject.AirBnB.controller;

import com.fullstackproject.AirBnB.dto.HotelDto;
import com.fullstackproject.AirBnB.dto.HotelInfoDto;
import com.fullstackproject.AirBnB.dto.HotelSearchRequest;
import com.fullstackproject.AirBnB.service.HotelService;
import com.fullstackproject.AirBnB.service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;
    @GetMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
        Page<HotelDto> page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
