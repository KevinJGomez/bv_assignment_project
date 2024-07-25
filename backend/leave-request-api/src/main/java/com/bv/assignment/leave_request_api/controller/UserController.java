package com.bv.assignment.leave_request_api.controller;

import com.bv.assignment.leave_request_api.models.RequestUser;
import com.bv.assignment.leave_request_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AuthService authService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestParam String username) {
        RequestUser user = authService.findByUsername("some-uuid", username);
        return ResponseEntity.ok().body(user);
    }
}

