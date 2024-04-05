package com.example.articlesproject.controller;

import com.example.articlesproject.dto.request.UserRequestDto;
import com.example.articlesproject.dto.response.JwtAuthenticationResponseDto;
import com.example.articlesproject.dto.response.UserResponseDto;
import com.example.articlesproject.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtAuthenticationResponseDto> authenticateUser(@RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(authenticationService.authenticate(requestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid UserRequestDto requestDto) {
        return ResponseEntity.ok(authenticationService.register(requestDto));
    }
}