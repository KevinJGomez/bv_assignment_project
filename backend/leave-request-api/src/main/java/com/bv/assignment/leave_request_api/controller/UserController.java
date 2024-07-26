package com.bv.assignment.leave_request_api.controller;

import com.bv.assignment.leave_request_api.models.RequestUser;
import com.bv.assignment.leave_request_api.service.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private AuthService authService;

    /*
     * Part of RBAC implementation
     * @In Request ID
     * @Out Confirmation
     *
     * */
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestParam String username) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "") + " ";
        logger.info(uuid + " UserController | getUserProfile Initiated.......");
        try{
            logger.info(uuid + " UserController | getUserProfile -> Calling the user service.......");
            RequestUser user = authService.findByUsername(uuid, username);
            return ResponseEntity.ok().body(user);
        }catch(Exception e){
            logger.info(uuid + " UserController | getUserProfile Failed.......Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }
}

