package com.bv.assignment.leave_request_api.controller;

import com.bv.assignment.leave_request_api.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bv.assignment.leave_request_api.service.AuthService;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LogManager.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    /*
     * Login Method for the Application
     * @In Credentials
     * @Out Auth Token
     *
     * */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "") + " ";

        try {
            logger.info(uuid + " AuthController: Login Method Called...Username: " + request.getUsername());

            RequestUser user = authService.findByUsername(uuid, request.getUsername());
            if (user == null) {
                return ResponseEntity.ok(new LoginResponse("1", "Failed", "No user found"));
            }
            else {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                boolean checkPass = bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword());
                if(checkPass){
                    String token = authService.generateToken(user);

                    return ResponseEntity.ok(new LoginResponse("0", "Success", token));
                }
                else {
                    String token = authService.generateToken(user);

                    return ResponseEntity.ok(new LoginResponse("1", "Failed", "Invalid Credentials"));
                }
            }

        }catch(Exception e) {
            logger.error(uuid + ": ERROR: AuthController Login: ");
            e.printStackTrace();

            return ResponseEntity.ok(new LoginResponse("1", "Failed", ""));
        }

    }

    /*
     * Register Method for the Application
     * @In Credentials
     * @Out Confirmation
     *
     * */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RequestUser user) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "") + " ";

        try {
            logger.info(uuid + " AuthController: register Method Called...Username: " + user.getUsername());

                try {
                    RequestUser.Role.valueOf(user.getRole().name());
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.ok(new LoginResponse("1", "Failed", "Invalid role"));
                }

            RequestUser newUser = authService.saveUser(uuid, user);
            return ResponseEntity.ok(new LoginResponse("0", "Success", "User Created: " + newUser.getUsername()));
        } catch (Exception e) {
            logger.error(uuid + ": ERROR: AuthController register: ");
            e.printStackTrace();

            return ResponseEntity.ok(new LoginResponse("1", "Failed", "Cannot Create User: " + user.getUsername()));
        }
    }

    /*
     * Register Method for the Application
     * @In Credentials
     * @Out Confirmation
     *
     * */
    @PostMapping("/verify")
    public ReturnTokenValidity verifyUser(@RequestBody GetToken token) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "") + " ";
        ReturnTokenValidity checkToken = new ReturnTokenValidity();
        try {
            logger.info(uuid + " AuthController: verifyUser Method Called...Token: " + token);
            checkToken = authService.validateToken(token.getToken());
            return checkToken;
        } catch (Exception e) {
            logger.error(uuid + ": ERROR: AuthController verifyUser: ");
            e.printStackTrace();
            checkToken.setValidToken("false");
            return checkToken;
        }
    }
}

