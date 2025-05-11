package com.fullstackproject.AirBnB.controller;

import com.fullstackproject.AirBnB.dto.*;
import com.fullstackproject.AirBnB.service.HotelService;
import com.fullstackproject.AirBnB.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController()
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;
    @GetMapping("/search")
    @Operation(summary = "Search hotels", tags = {"Browse Hotels"})
    public ResponseEntity<Page<HotelPriceDto>> searchHotels(
            @RequestParam String city,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam Integer roomsCount,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        HotelSearchRequest hotelSearchRequest = new HotelSearchRequest(city, startDate, endDate, roomsCount, page, size);
        var pageResult = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("/{hotelId}/info")
    @Operation(summary = "Get a hotel info by hotelId", tags = {"Browse Hotels"})
    public ResponseEntity<HotelInfoDto> getHotelInfo(
            @PathVariable Long hotelId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam Long roomsCount) {

        HotelInfoRequestDto hotelInfoRequestDto = new HotelInfoRequestDto(startDate, endDate, roomsCount);
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId, hotelInfoRequestDto));
    }
}
