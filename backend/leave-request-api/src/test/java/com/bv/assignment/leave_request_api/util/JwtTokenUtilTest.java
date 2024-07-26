package com.bv.assignment.leave_request_api.util;

import com.bv.assignment.leave_request_api.models.RequestUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenUtilTest {

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    private RequestUser requestUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtTokenUtil.setSecret("dasjdhvasdjsavj^$@^$@^%$ghvdasjgdvasjgdvasjdagsvdajdgvsa2161247162&^%&%&^%&^%&^%dahsgdcashdcashdcas");

        requestUser = new RequestUser();
        requestUser.setUsername("kevin");
        requestUser.setPassword("admin@123");
        requestUser.setRole(RequestUser.Role.ADMIN);
    }

    @Test
    void testGenerateToken() {
        String token = jwtTokenUtil.generateToken(requestUser);
        assertNotNull(token);
    }

    @Test
    void testValidateToken() {
        String token = jwtTokenUtil.generateToken(requestUser);
        assertTrue(jwtTokenUtil.validateToken(token, requestUser));
    }

    @Test
    void testExtractUsername() {
        String token = jwtTokenUtil.generateToken(requestUser);
        String username = jwtTokenUtil.extractUsername(token);
        assertEquals("kevin", username);
    }

    @Test
    void testIsTokenExpired() {
        String token = jwtTokenUtil.generateToken(requestUser);
        assertFalse(jwtTokenUtil.isTokenExpired(token));
    }
}


