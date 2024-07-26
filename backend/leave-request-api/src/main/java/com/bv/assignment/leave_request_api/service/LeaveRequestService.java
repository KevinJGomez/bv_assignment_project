package com.bv.assignment.leave_request_api.service;

import com.bv.assignment.leave_request_api.models.LeaveRequest;
import com.bv.assignment.leave_request_api.models.RequestUser;
import com.bv.assignment.leave_request_api.repository.LeaveRepository;
import com.bv.assignment.leave_request_api.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestService {

    private static final Logger logger = LogManager.getLogger(LeaveRequestService.class);

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private UserRepository userRepository;

    public LeaveRequest saveLeaveRequest(String uuid, LeaveRequest data) {
        logger.info(uuid + " LeaveRequestService | saveLeaveRequest Initiated.......");
        try{
            logger.info(uuid + " LeaveRequestService | saveLeaveRequest -> Calling Leave Repository.......");
            return leaveRepository.save(data);
        }catch(Exception e){
            logger.info(uuid + " LeaveRequestService | saveLeaveRequest Failed.......Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<LeaveRequest> getLeavesByUser(String uuid, RequestUser user){
        logger.info(uuid + " LeaveRequestService | getLeavesByUser Initiated.......");
        try{
            logger.info(uuid + " LeaveRequestService | getLeavesByUser -> Calling Leave Repository.......");
            return leaveRepository.findByUser(user);
        }catch(Exception e){
            logger.info(uuid + " LeaveRequestService | getLeavesByUser Failed.......Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Long findUserIdByUsername(String uuid, String username){
        logger.info(uuid + " LeaveRequestService | findUserIdByUsername Initiated.......");
        try{
            logger.info(uuid + " LeaveRequestService | findUserIdByUsername -> Calling User Repository.......");
            return userRepository.findUserIdByUsername(username);
        }catch(Exception e){
            logger.info(uuid + " LeaveRequestService | findUserIdByUsername Failed.......Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public RequestUser findUserById(String uuid, Long id){
        logger.info(uuid + " LeaveRequestService | findUserById Initiated.......");
        try{
            logger.info(uuid + " LeaveRequestService | findUserById -> Calling User Repository.......");
            return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        }catch(Exception e){
            logger.info(uuid + " LeaveRequestService | findUserById Failed.......Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Optional<LeaveRequest> findById(String uuid, Long id) {
        logger.info(uuid + " LeaveRequestService | findById Initiated.......");
        try{
            logger.info(uuid + " LeaveRequestService | findById -> Calling Leave Repository.......");
            return leaveRepository.findById(id);
        }catch(Exception e){
            logger.info(uuid + " LeaveRequestService | findById Failed.......Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    public void deleteLeaveRequest(String uuid, Long id) {
        logger.info(uuid + " LeaveRequestService | deleteLeaveRequest Initiated.......");
        try{
            logger.info(uuid + " LeaveRequestService | deleteLeaveRequest -> Calling Leave Repository.......");
            leaveRepository.deleteById(id);
        }catch(Exception e){
            logger.info(uuid + " LeaveRequestService | deleteLeaveRequest Failed.......Error: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
