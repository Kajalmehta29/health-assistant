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
    public ResponseEntity<ApiResponse<String>> register(@RequestBody User user) {

        userService.registerUser(user);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User registered successfully",
                        null
                )
        );
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody User user) {

        User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
        String token = jwtUtil.generateToken(loggedInUser.getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Login successful",
                        token
                )
        );
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<String>> updateUser(
            @PathVariable Long id,
            @RequestBody User user) {

        userService.updateUser(id, user);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User updated successfully",
                        null
                )
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {

        UserResponse user = userService.getUserProfile(id);

        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User deleted successfully",
                        null
                )
        );
    }
    @GetMapping("/test")
    public String adminOnly() {
        return "Only admin can see this";
    }
}