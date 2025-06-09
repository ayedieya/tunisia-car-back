package com.example.TunisiaCars_Back.service;


import com.example.TunisiaCars_Back.entity.Role;
import com.example.TunisiaCars_Back.entity.User;
import com.example.TunisiaCars_Back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.user);
        return userRepository.save(user);
    }


    @Override
    public User updateUser(String id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            updatedUser.setId(id);
            return userRepository.save(updatedUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

//    public Optional<User> authenticateUser(String email, String rawPassword) {
//        Optional<User> userOpt = userRepository.findByEmail(email);
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
//                return Optional.of(user);
//            }
//        }
//        return Optional.empty();
//    }

//    public Optional<User> authenticateUser(String email, String rawPassword) {
//        if (email.equals("admin@tunisiacars.com") && rawPassword.equals("admin123")) {
//            User admin = new User();
//            admin.setEmail(email);
//            admin.setRole(Role.valueOf("admin"));
//            // Si besoin, on peut définir d'autres infos admin ici
//            return Optional.of(admin);
//        }
//
//        Optional<User> userOpt = userRepository.findByEmail(email);
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
//                return Optional.of(user);
//            }
//        }
//        return Optional.empty();
//    }


    @Override
    public Optional<User> authenticateUser(String email, String rawPassword) {
        if (email.equals("admin@tunisiacars.com") && rawPassword.equals("admin123")) {
            User admin = new User();
            admin.setEmail(email);
            admin.setRole(Role.admin); // Enum
            return Optional.of(admin);
        }

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }



}

