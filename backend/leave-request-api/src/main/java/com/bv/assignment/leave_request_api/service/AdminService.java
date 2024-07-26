package com.bv.assignment.leave_request_api.service;

import com.bv.assignment.leave_request_api.models.RequestUser;
import com.bv.assignment.leave_request_api.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private static final Logger logger = LogManager.getLogger(AdminService.class);

    @Autowired
    private UserRepository userRepository;

    /*
     * Admin only accessible
     * To get All user details
     * */
    public List<RequestUser> getAllUsers(String uuid){
        logger.info(uuid + " AdminService | Get All Users Initiated.......");
        try{
            logger.info(uuid + " AdminService | Calling the user repository.......");
            return userRepository.findAll();
        } catch(Exception e){
            logger.info(uuid + " AdminService | Get All users Failed.......Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
