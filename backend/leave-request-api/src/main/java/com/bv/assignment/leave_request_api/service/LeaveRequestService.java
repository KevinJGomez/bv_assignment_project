package com.bv.assignment.leave_request_api.service;

import com.bv.assignment.leave_request_api.models.LeaveRequest;
import com.bv.assignment.leave_request_api.models.RequestUser;
import com.bv.assignment.leave_request_api.models.ReturnTokenValidity;
import com.bv.assignment.leave_request_api.repository.LeaveRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LeaveRequestService {

    private static final Logger logger = LogManager.getLogger(LeaveRequestService.class);

    @Autowired
    private LeaveRepository leaveRepository;

    public LeaveRequest saveLeaveRequest(LeaveRequest data) {
        try{
            return leaveRepository.save(data);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
