package com.health.healthassistant.controller;

import com.health.healthassistant.model.User;
import com.health.healthassistant.security.JwtUtil;
import com.health.healthassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.health.healthassistant.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import com.health.healthassistant.dto.UserResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok(new ApiResponse("User registered successfully", true));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());

        String token = jwtUtil.generateToken(loggedInUser.getId());

        return ResponseEntity.ok(token);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(
            @PathVariable Long id,
            @RequestBody User user) {

        userService.updateUser(id, user);

        return ResponseEntity.ok(new ApiResponse("User updated successfully", true));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {

        UserResponse user = userService.getUserProfile(id);

        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(new ApiResponse("User deleted successfully", true));
    }
}