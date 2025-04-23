package com.fullstackproject.AirBnB.service;

import com.fullstackproject.AirBnB.dto.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto createNewRoom(Long hotelId,RoomDto roomDto);
    List<RoomDto> getAllRoomsInHotel(Long hotelId);
    RoomDto getRoomById(Long roomId);
    void deleteRoomById(Long roomId);
    RoomDto updateRoomByID(Long hotelId, RoomDto roomDto, Long roomId);
}
