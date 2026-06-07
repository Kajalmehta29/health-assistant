package com.health.healthassistant.service;

import com.health.healthassistant.model.User;
import com.health.healthassistant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.health.healthassistant.exception.UserNotFoundException;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User not found with this email");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}