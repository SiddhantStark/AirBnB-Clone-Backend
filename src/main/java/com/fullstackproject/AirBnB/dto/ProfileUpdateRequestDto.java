package com.fullstackproject.AirBnB.dto;

import lombok.Data;

import java.time.LocalDate;
import com.fullstackproject.AirBnB.entity.enums.Gender;

@Data
public class ProfileUpdateRequestDto {
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
}
