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


    /*
     * Save API for Leave Requests
     * @In Leave Request
     * @Out Confirmation
     *
     * */
    @PostMapping("/leave-requests")
    public ResponseEntity<?> saveLeaveRequest(@RequestBody LeaveRequest data, HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "") + " ";
        logger.info(uuid + " LeaveRequestController | saveLeaveRequest Initiated.......");
        try {
            String token = extractTokenFromRequest(uuid, request);
            String username = authService.getUsernameFromToken(token);
            Long getUserId = leaveRequestService.findUserIdByUsername(uuid, username);
            RequestUser user = leaveRequestService.findUserById(uuid, getUserId);
            data.setUser(user);

            logger.info(uuid + " LeaveRequestController | saveLeaveRequest -> Calling Service Method.......");
            LeaveRequest leaveRequest = leaveRequestService.saveLeaveRequest(uuid, data);
            return ResponseEntity.ok(new LoginResponse("0", "Success", "Leave Applied: " + leaveRequest.getLeaveType()));
        } catch (Exception e) {
            logger.info(uuid + " LeaveRequestController | saveLeaveRequest Failed.......Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new LoginResponse("1", "Failed", null));
        }
    }

    /*
     * Get All Requests as per the userId
     * @In Token
     * @Out Confirmation
     *
     * */
    @GetMapping("/leave-requests")
    public List<LeaveRequest> getLeaves(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "") + " ";
        logger.info(uuid + " LeaveRequestController | getLeaves Initiated.......");
        try {
            String token = extractTokenFromRequest(uuid, request);
            String username = authService.getUsernameFromToken(token);
            Long getUserId = leaveRequestService.findUserIdByUsername(uuid, username);
            RequestUser user = leaveRequestService.findUserById(uuid, getUserId);

            logger.info(uuid + " LeaveRequestController | getLeaves -> Calling Service Method.......");
            return leaveRequestService.getLeavesByUser(uuid, user);

        } catch (Exception e) {
            logger.info(uuid + " LeaveRequestController | getLeaves -> getLeaves failed!.......Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /*
     * Modifying the existing request
     * @In Leave Request
     * @Out Confirmation
     *
     * */
    @PutMapping("/leave-requests")
    public ResponseEntity<?> updateLeaveRequest(@RequestBody LeaveRequest data, HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "") + " ";
        logger.info(uuid + " LeaveRequestController | updateLeaveRequest Initiated.......");
        try {
            String token = extractTokenFromRequest(uuid, request);

            logger.info(uuid + " LeaveRequestController | updateLeaveRequest -> Calling user service.......");
            String username = authService.getUsernameFromToken(token);

            logger.info(uuid + " LeaveRequestController | updateLeaveRequest -> Calling service methods.......");
            Long getUserId = leaveRequestService.findUserIdByUsername(uuid, username);
            RequestUser user = leaveRequestService.findUserById(uuid, getUserId);

            LeaveRequest existingLeaveRequest = leaveRequestService.findById(uuid, data.getId())
                    .orElseThrow(() -> new RuntimeException("Leave request not found"));

            if (!existingLeaveRequest.getUser().getId().equals(user.getId())) {
                return  ResponseEntity.ok(new LoginResponse("0", "Success", "Cannot Find the Leave Application"));
            }

            existingLeaveRequest.setLeaveType(data.getLeaveType());
            existingLeaveRequest.setStartDate(data.getStartDate());
            existingLeaveRequest.setEndDate(data.getEndDate());
            existingLeaveRequest.setReason(data.getReason());

            logger.info(uuid + " LeaveRequestController | updateLeaveRequest -> Calling saving service method.......");
            LeaveRequest updatedLeaveRequest = leaveRequestService.saveLeaveRequest(uuid, existingLeaveRequest);

            return ResponseEntity.ok(new LoginResponse("0", "Success", "Leave Request Updated: " + updatedLeaveRequest.getLeaveType()));
        } catch (Exception e) {
            logger.info(uuid + " LeaveRequestController | updateLeaveRequest Failed.......Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new LoginResponse("1", "Failed", null));
        }
    }

    /*
     * Delete a specific request
     * @In Request ID
     * @Out Confirmation
     *
     * */
    @DeleteMapping("/leave-requests/{id}")
    public ResponseEntity<?> deleteLeaveRequest(@PathVariable Long id, HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "") + " ";

        logger.info(uuid + " LeaveRequestController | deleteLeaveRequest Initiated.......");
        try {
            String token = extractTokenFromRequest(uuid, request);

            logger.info(uuid + " LeaveRequestController | deleteLeaveRequest -> Calling user service.......");
            String username = authService.getUsernameFromToken(token);

            logger.info(uuid + " LeaveRequestController | deleteLeaveRequest -> Calling leave service methods.......");
            Long getUserId = leaveRequestService.findUserIdByUsername(uuid, username);
            RequestUser user = leaveRequestService.findUserById(uuid, getUserId);
            LeaveRequest existingLeaveRequest = leaveRequestService.findById(uuid, id)
                    .orElseThrow(() -> new RuntimeException("Leave request not found"));
            if (!existingLeaveRequest.getUser().getId().equals(user.getId())) {
                return  ResponseEntity.ok(new LoginResponse("0", "Success", "You can delete your own leave application only!"));
            }

            logger.info(uuid + " LeaveRequestController | deleteLeaveRequest -> Calling leave delete service method.......");
            leaveRequestService.deleteLeaveRequest(uuid, id);

            return ResponseEntity.ok(new LoginResponse("0", "Success", "Leave Request Deleted"));
        } catch (Exception e) {
            logger.info(uuid + " LeaveRequestController | deleteLeaveRequest Failed.......Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new LoginResponse("1", "Failed", null));
        }
    }

    private String extractTokenFromRequest(String uuid, HttpServletRequest request) {
        logger.info(uuid + " LeaveRequestController | extractTokenFromRequest initiated.......");
        try{
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                logger.info(uuid + " LeaveRequestController | Token Extraction Success!.......");
                return bearerToken.substring(7);
            }
            else{
                logger.info(uuid + " LeaveRequestController | Token Extraction was not Success!.......");
                return null;
            }
        }catch(Exception e){
            logger.info(uuid + " LeaveRequestController | Token Extraction Failed.......Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
