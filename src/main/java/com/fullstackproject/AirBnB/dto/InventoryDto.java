package com.fullstackproject.AirBnB.dto;

import com.fullstackproject.AirBnB.entity.Hotel;
import com.fullstackproject.AirBnB.entity.Room;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InventoryDto {
    private Long id;
    private LocalDate date;
    private Integer bookedCount;
    private Integer reservedCount;
    private Integer totalCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BigDecimal surgeFactor;
    private BigDecimal price;
    private Boolean closed;
}
