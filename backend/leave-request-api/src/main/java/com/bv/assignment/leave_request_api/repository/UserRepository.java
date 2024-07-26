package com.bv.assignment.leave_request_api.repository;

import com.bv.assignment.leave_request_api.models.RequestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<RequestUser, Long> {
    RequestUser findByUsername(String username);

    @Query("SELECT u.id FROM RequestUser u WHERE u.username = :username")
    Long findUserIdByUsername(@Param("username") String username);
}