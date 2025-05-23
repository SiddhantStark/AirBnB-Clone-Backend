package com.fullstackproject.AirBnB.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelInfoRequestDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long roomsCount;
}
