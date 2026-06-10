package com.health.healthassistant.service;

import com.health.healthassistant.model.User;
import com.health.healthassistant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.health.healthassistant.exception.UserNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import com.health.healthassistant.dto.UserResponse;
import java.time.LocalDate;
import java.time.Period;

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
    public int calculateAge(LocalDate dob) {
        return Period.between(dob, LocalDate.now()).getYears();
    }
    public User updateUser(Long id, User updatedUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setDob(updatedUser.getDob());
        existingUser.setGender(updatedUser.getGender());
        existingUser.setHeight(updatedUser.getHeight());
        existingUser.setWeight(updatedUser.getWeight());
        existingUser.setAllergies(updatedUser.getAllergies());

        return userRepository.save(existingUser);
    }
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public UserResponse getUserProfile(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        int age = Period.between(user.getDob(), LocalDate.now()).getYears();

        return new UserResponse(
                user.getName(),
                user.getEmail(),
                age,
                user.getGender(),
                user.getHeight(),
                user.getWeight(),
                user.getAllergies()
        );
    }
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }
}