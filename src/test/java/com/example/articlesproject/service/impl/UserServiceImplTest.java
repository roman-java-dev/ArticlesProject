package com.example.articlesproject.service.impl;

import com.example.articlesproject.exception.DataProcessingException;
import com.example.articlesproject.model.User;
import com.example.articlesproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User addedUser = userService.addUser(user);

        assertEquals(user, addedUser);
    }

    @Test
    void findByUsername_whenUserExists() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User foundUser = userService.findByUsername(username);

        assertEquals(user, foundUser);
    }

    @Test
    void findByUsername_whenUserDoesNotExist() {
        String username = "nonexistentuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(DataProcessingException.class, () -> userService.findByUsername(username));
    }
}
