package com.fullstackproject.AirBnB.dto;

import com.fullstackproject.AirBnB.entity.HostelContactInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class HotelDto {
    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private HostelContactInfo contactInfo;
}
