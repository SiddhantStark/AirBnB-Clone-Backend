package com.fullstackproject.AirBnB.dto;

import com.fullstackproject.AirBnB.entity.HostelContactInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelPriceResponseDto {
    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private HostelContactInfo contactInfo;
    private Double price;
}
