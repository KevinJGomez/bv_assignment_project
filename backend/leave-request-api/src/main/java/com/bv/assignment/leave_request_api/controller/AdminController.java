package com.bv.assignment.leave_request_api.controller;

import com.bv.assignment.leave_request_api.models.RequestUser;
import com.bv.assignment.leave_request_api.repository.UserRepository;
import com.bv.assignment.leave_request_api.service.AdminService;
import com.bv.assignment.leave_request_api.service.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger logger = LogManager.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    /*
     * Admin only accessible
     * To get All user details
     * */
    @GetMapping("/users")
    public List<RequestUser> getAllUsers() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "") + " ";
        logger.info(uuid + " AdminController | Get All Users Initiated.......");
        try{
            return adminService.getAllUsers(uuid);
        }catch(Exception e){
            logger.info(uuid + " AdminController | Get All Users Failed.......Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}

