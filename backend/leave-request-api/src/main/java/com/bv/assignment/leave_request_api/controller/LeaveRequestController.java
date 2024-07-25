package com.bv.assignment.leave_request_api.controller;

import com.bv.assignment.leave_request_api.models.GetToken;
import com.bv.assignment.leave_request_api.models.LeaveRequest;
import com.bv.assignment.leave_request_api.models.LoginResponse;
import com.bv.assignment.leave_request_api.models.ReturnTokenValidity;
import com.bv.assignment.leave_request_api.repository.LeaveRepository;
import com.bv.assignment.leave_request_api.service.AuthService;
import com.bv.assignment.leave_request_api.service.LeaveRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class LeaveRequestController {
    private static final Logger logger = LogManager.getLogger(LeaveRequestController.class);

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private AuthService authService;

    @Autowired
    private LeaveRepository leaveRepository;
    /*
     * Save API for Leave Requests
     * @In Leave Request
     * @Out ResponseEntity
     *
     * */
    @PostMapping("/leave-requests")
    public ResponseEntity<?> saveLeaveRequest(@RequestBody LeaveRequest data, HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "") + " ";

        try {
            String token = extractTokenFromRequest(request);
            String username = authService.getUsernameFromToken(token);
            data.setUserId(username);
            LeaveRequest leaveRequest = leaveRequestService.saveLeaveRequest(data);
            return ResponseEntity.ok(new LoginResponse("0", "Success", "Leave Applied: " + leaveRequest.getLeaveType()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new LoginResponse("1", "Failed", null));
        }
    }

    @GetMapping("/leave-requests")
    public List<LeaveRequest> getLeaves(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "") + " ";
        try {
            String token = extractTokenFromRequest(request);
            String username = authService.getUsernameFromToken(token);
            return leaveRepository.findByUserId(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
