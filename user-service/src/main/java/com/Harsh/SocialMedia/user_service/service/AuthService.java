package com.Harsh.SocialMedia.user_service.service;

import com.Harsh.SocialMedia.user_service.dto.LoginRequestDto;
import com.Harsh.SocialMedia.user_service.dto.SignUpRequestDto;
import com.Harsh.SocialMedia.user_service.dto.UserDto;
import com.Harsh.SocialMedia.user_service.entity.User;
import com.Harsh.SocialMedia.user_service.exception.BadRequestException;
import com.Harsh.SocialMedia.user_service.exception.ResourceNotFoundException;
import com.Harsh.SocialMedia.user_service.repository.UserRepository;
import com.Harsh.SocialMedia.user_service.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;
    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        boolean exists=userRepository.existsByEmail(signUpRequestDto.getEmail());
        if(exists){
            throw new BadRequestException("User already exists");
        }
        User newUser = modelMapper.map(signUpRequestDto, User.class);
        newUser.setPassword(PasswordUtils.hashPassword(signUpRequestDto.getPassword()));
        newUser = userRepository.save(newUser);

        return modelMapper.map(newUser, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        User user=userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("User not found with email:"+loginRequestDto.getEmail()));
        boolean isPasswordMatch=PasswordUtils.checkPassword(loginRequestDto.getPassword(),user.getPassword());
        if(!isPasswordMatch){
            throw new BadRequestException("Incorrect password");
        }
        return jwtService.generateAccessToken(user);
    }
}
