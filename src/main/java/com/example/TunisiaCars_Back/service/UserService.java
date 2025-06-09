package com.example.TunisiaCars_Back.service;


import com.example.TunisiaCars_Back.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(String id);
    User createUser(User user);
    User updateUser(String id, User user);
    void deleteUser(String id);

    Optional<User> authenticateUser(String email, String rawPassword);

}
