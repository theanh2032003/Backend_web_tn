package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUserName(String username);

    Optional<User> findByEmail(String email);

    User save(User user);

    List<User> findAll();

    Optional<User> findById(Long userId);

    void delete(User user);
}
