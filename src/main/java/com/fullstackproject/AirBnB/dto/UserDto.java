package com.fullstackproject.AirBnB.dto;

import com.fullstackproject.AirBnB.entity.enums.Gender;
import com.fullstackproject.AirBnB.entity.enums.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Set<Role> roles;
}
