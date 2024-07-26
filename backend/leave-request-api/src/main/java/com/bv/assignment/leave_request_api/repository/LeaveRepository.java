package com.bv.assignment.leave_request_api.repository;

import com.bv.assignment.leave_request_api.models.LeaveRequest;
import com.bv.assignment.leave_request_api.models.RequestUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByUser(RequestUser user);
    void deleteById(Long id);
}
