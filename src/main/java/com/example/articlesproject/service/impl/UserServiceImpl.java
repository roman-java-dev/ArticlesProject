package com.example.articlesproject.service.impl;

import com.example.articlesproject.exception.DataProcessingException;
import com.example.articlesproject.model.User;
import com.example.articlesproject.repository.UserRepository;
import com.example.articlesproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new DataProcessingException("Can`t find user by username: " + username)
        );
    }
}