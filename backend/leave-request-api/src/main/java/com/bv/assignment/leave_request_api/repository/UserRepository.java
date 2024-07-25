package com.bv.assignment.leave_request_api.repository;

import com.bv.assignment.leave_request_api.models.RequestUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<RequestUser, Long> {
    RequestUser findByUsername(String username);
}