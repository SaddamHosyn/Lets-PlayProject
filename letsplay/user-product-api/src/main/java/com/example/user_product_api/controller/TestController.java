package com.example.user_product_api.controller;

import com.example.user_product_api.entity.User;
import com.example.user_product_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello! Your Spring Boot API is working!";
    }
    
    @PostMapping("/create-user")
    public String createTestUser() {
        User testUser = new User("Test User", "test@example.com", "password123", "USER");
        userRepository.save(testUser);
        return "Test user created successfully!";
    }
    
    @GetMapping("/users")
    public Object getUsers() {
        return userRepository.findAll();
    }
}
