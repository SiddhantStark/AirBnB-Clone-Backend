package com.fullstackproject.AirBnB.controller;

import com.fullstackproject.AirBnB.dto.RoomDto;
import com.fullstackproject.AirBnB.entity.Room;
import com.fullstackproject.AirBnB.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomAdminController {
    private final RoomService roomService;
    @PostMapping
    private ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId, @RequestBody RoomDto roomDto){
        RoomDto room = roomService.createNewRoom(hotelId, roomDto);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<List<RoomDto>> getAllRoomsInHotels(@PathVariable Long hotelId){
        return ResponseEntity.ok(roomService.getAllRoomsInHotel(hotelId));
    }

    @GetMapping("/{roomId}")
    private ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId){
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @DeleteMapping("/{roomId}")
    private ResponseEntity<RoomDto> deleteRoomById(@PathVariable Long roomId){
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }
}

