package com.bv.assignment.leave_request_api.controller;

import com.bv.assignment.leave_request_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AuthService authService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        // Implement logic to get all users
        return ResponseEntity.ok().body("Admin endpoint: Get all users");
    }
}

