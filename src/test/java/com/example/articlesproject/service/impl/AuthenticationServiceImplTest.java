package com.example.articlesproject.service.impl;

import com.example.articlesproject.dto.request.UserRequestDto;
import com.example.articlesproject.dto.response.JwtAuthenticationResponseDto;
import com.example.articlesproject.dto.response.UserResponseDto;
import com.example.articlesproject.exception.CustomAuthenticationException;
import com.example.articlesproject.model.User;
import com.example.articlesproject.security.JwtService;
import com.example.articlesproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserService userService;
    @Mock
    private ObjectMapper mapper;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate_withValidCredentials() {
        String username = "testuser";
        String password = "testpassword";
        UserRequestDto requestDto = new UserRequestDto(username, password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        when(userService.findByUsername(username)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("generated_token");

        JwtAuthenticationResponseDto responseDto = authenticationService.authenticate(requestDto);

        assertEquals("generated_token", responseDto.getToken());
        verify(userService).findByUsername(username);
        verify(passwordEncoder).matches(password, user.getPassword());
        verify(jwtService).generateToken(user);
    }

    @Test
    void authenticate_withInvalidCredentials() {
        String username = "testuser";
        String password = "testpassword";
        UserRequestDto requestDto = new UserRequestDto(username, password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("different_password"));

        when(userService.findByUsername(username)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        assertThrows(CustomAuthenticationException.class, () -> authenticationService.authenticate(requestDto));
    }

    @Test
    void register() {
        UserRequestDto requestDto = new UserRequestDto("testuser", "testpassword");
        User user = new User();
        user.setUsername(requestDto.username());
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        user.setRole(User.Role.USER);

        when(mapper.convertValue(requestDto, User.class)).thenReturn(user);
        when(userService.addUser(any())).thenReturn(user);
        when(mapper.convertValue(user, UserResponseDto.class)).thenReturn(new UserResponseDto(user.getUsername()));

        UserResponseDto responseDto = authenticationService.register(requestDto);

        assertEquals(user.getUsername(), responseDto.username());
        assertEquals(user.getRole(), User.Role.USER);
        verify(mapper).convertValue(requestDto, User.class);
        verify(passwordEncoder, times(2)).encode(requestDto.password());
        verify(userService).addUser(user);
        verify(mapper).convertValue(user, UserResponseDto.class);
    }
}
