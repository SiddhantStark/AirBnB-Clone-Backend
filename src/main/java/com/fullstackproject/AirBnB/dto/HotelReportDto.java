package com.fullstackproject.AirBnB.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class HotelReportDto {
    private Long bookingDto;
    private BigDecimal totalRevenue;
    private BigDecimal avgRevenue;
}
