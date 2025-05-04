package com.fullstackproject.AirBnB.service;

import com.fullstackproject.AirBnB.dto.ProfileUpdateRequestDto;
import com.fullstackproject.AirBnB.dto.UserDto;
import com.fullstackproject.AirBnB.entity.User;
import com.fullstackproject.AirBnB.exception.ResourceNotFoundException;
import com.fullstackproject.AirBnB.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.fullstackproject.AirBnB.util.AppUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not find with ID: " + id));
    }

    @Override
    public void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto) {
        User user = getCurrentUser();
        if(profileUpdateRequestDto.getDateOfBirth() != null){
            user.setDateOfBirth(profileUpdateRequestDto.getDateOfBirth());
        }
        if(profileUpdateRequestDto.getGender() != null){
            user.setGender(profileUpdateRequestDto.getGender());
        }
        if(profileUpdateRequestDto.getName() != null){
            user.setName(profileUpdateRequestDto.getName());
        }

        userRepository.save(user);
    }

    @Override
    public UserDto getMyProfile() {
        log.info("Getting the user profile for user with ID: {}", getCurrentUser().getId());
        return modelMapper.map(getCurrentUser(), UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
