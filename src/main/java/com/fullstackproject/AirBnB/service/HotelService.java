package com.fullstackproject.AirBnB.service;

import com.fullstackproject.AirBnB.dto.HotelDto;
import com.fullstackproject.AirBnB.entity.Hotel;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long id);
    HotelDto updateHotelById(Long id, HotelDto hotelDto);
    void deleteHotelById(Long Id);

    void activateHotel(Long id);
}
