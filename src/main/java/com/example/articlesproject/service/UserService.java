package com.example.articlesproject.service;

import com.example.articlesproject.model.User;

public interface UserService {
    User addUser(User user);

    User findByUsername(String username);
}