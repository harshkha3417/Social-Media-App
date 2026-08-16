package com.Harsh.SocialMedia.user_service.controller;

import com.Harsh.SocialMedia.user_service.dto.LoginRequestDto;
import com.Harsh.SocialMedia.user_service.dto.SignUpRequestDto;
import com.Harsh.SocialMedia.user_service.dto.UserDto;
import com.Harsh.SocialMedia.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        return new ResponseEntity<>(authService.signUp(signUpRequestDto), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<String> signup(@RequestBody LoginRequestDto loginRequestDto) {
        String token=authService.login(loginRequestDto);
        return ResponseEntity.ok(token);
    }
}
