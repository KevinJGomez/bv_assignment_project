package com.bv.assignment.leave_request_api.controller;

import com.bv.assignment.leave_request_api.models.GetToken;
import com.bv.assignment.leave_request_api.models.*;
import com.bv.assignment.leave_request_api.models.LoginResponse;
import com.bv.assignment.leave_request_api.models.ReturnTokenValidity;
import com.bv.assignment.leave_request_api.repository.LeaveRepository;
import com.bv.assignment.leave_request_api.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;
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
            Long getUserId = userRepository.findUserIdByUsername(username);
            RequestUser user = userRepository.findById(getUserId).orElseThrow(() -> new RuntimeException("User not found"));
            data.setUser(user);
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
            Long getUserId = userRepository.findUserIdByUsername(username);
            RequestUser user = userRepository.findById(getUserId).orElseThrow(() -> new RuntimeException("User not found"));
            return leaveRepository.findByUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("/leave-requests")
    public ResponseEntity<?> updateLeaveRequest(@RequestBody LeaveRequest data, HttpServletRequest request) {
        try {
            // Extract the token and get the username
            String token = extractTokenFromRequest(request);
            String username = authService.getUsernameFromToken(token);
            Long getUserId = userRepository.findUserIdByUsername(username);
            RequestUser user = userRepository.findById(getUserId).orElseThrow(() -> new RuntimeException("User not found"));

            // Find the existing leave request
            LeaveRequest existingLeaveRequest = leaveRequestService.findById(data.getId())
                    .orElseThrow(() -> new RuntimeException("Leave request not found"));

            // Check if the user owns the leave request
            if (!existingLeaveRequest.getUser().getId().equals(user.getId())) {
                return  ResponseEntity.ok(new LoginResponse("0", "Success", "Cannot Find the Leave Application"));
            }

            // Update the leave request details
            existingLeaveRequest.setLeaveType(data.getLeaveType());
            existingLeaveRequest.setStartDate(data.getStartDate());
            existingLeaveRequest.setEndDate(data.getEndDate());
            existingLeaveRequest.setReason(data.getReason());

            // Save the updated leave request
            LeaveRequest updatedLeaveRequest = leaveRequestService.saveLeaveRequest(existingLeaveRequest);

            return ResponseEntity.ok(new LoginResponse("0", "Success", "Leave Request Updated: " + updatedLeaveRequest.getLeaveType()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new LoginResponse("1", "Failed", null));
        }
    }

    @DeleteMapping("/leave-requests/{id}")
    public ResponseEntity<?> deleteLeaveRequest(@PathVariable Long id, HttpServletRequest request) {
        try {
            String token = extractTokenFromRequest(request);
            String username = authService.getUsernameFromToken(token);
            Long getUserId = userRepository.findUserIdByUsername(username);
            RequestUser user = userRepository.findById(getUserId).orElseThrow(() -> new RuntimeException("User not found"));

            LeaveRequest existingLeaveRequest = leaveRequestService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Leave request not found"));

            if (!existingLeaveRequest.getUser().getId().equals(user.getId())) {
                return  ResponseEntity.ok(new LoginResponse("0", "Success", "You can delete your own leave application only!"));
            }

            leaveRequestService.deleteLeaveRequest(id);

            return ResponseEntity.ok(new LoginResponse("0", "Success", "Leave Request Deleted"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new LoginResponse("1", "Failed", null));
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
