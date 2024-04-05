package com.example.articlesproject.service;

import com.example.articlesproject.dto.request.UserRequestDto;
import com.example.articlesproject.dto.response.JwtAuthenticationResponseDto;
import com.example.articlesproject.dto.response.UserResponseDto;

public interface AuthenticationService {
    JwtAuthenticationResponseDto authenticate(UserRequestDto requestDto);

    UserResponseDto register(UserRequestDto requestDto);
}