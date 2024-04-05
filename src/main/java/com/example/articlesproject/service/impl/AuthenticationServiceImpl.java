package com.example.articlesproject.service.impl;

import com.example.articlesproject.dto.request.UserRequestDto;
import com.example.articlesproject.dto.response.JwtAuthenticationResponseDto;
import com.example.articlesproject.dto.response.UserResponseDto;
import com.example.articlesproject.exception.CustomAuthenticationException;
import com.example.articlesproject.model.User;
import com.example.articlesproject.security.JwtService;
import com.example.articlesproject.service.AuthenticationService;
import com.example.articlesproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ObjectMapper mapper;

    @Override
    public JwtAuthenticationResponseDto authenticate(UserRequestDto requestDto) {
        var user = userService.findByUsername(requestDto.username());
        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new CustomAuthenticationException("Invalid username or password");
        }
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponseDto.builder().token(jwt).build();
    }

    @Override
    public UserResponseDto register(UserRequestDto requestDto) {
        User userToSave = mapper.convertValue(requestDto, User.class);
        userToSave.setPassword(passwordEncoder.encode(requestDto.password()));
        userToSave.setRole(User.Role.USER);
        return mapper.convertValue(userService.addUser(userToSave), UserResponseDto.class);
    }
}