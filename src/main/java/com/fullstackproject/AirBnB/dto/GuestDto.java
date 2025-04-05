package com.fullstackproject.AirBnB.dto;

import com.fullstackproject.AirBnB.entity.User;
import com.fullstackproject.AirBnB.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private LocalDateTime createdAt;
    private Gender gender;
    private Integer age;
}
