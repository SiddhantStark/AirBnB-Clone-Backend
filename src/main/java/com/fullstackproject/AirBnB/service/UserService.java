package com.fullstackproject.AirBnB.service;

import com.fullstackproject.AirBnB.dto.ProfileUpdateRequestDto;
import com.fullstackproject.AirBnB.dto.UserDto;
import com.fullstackproject.AirBnB.entity.User;

public interface UserService {
    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
