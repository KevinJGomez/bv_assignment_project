package com.bv.assignment.leave_request_api.controller;

import com.bv.assignment.leave_request_api.models.RequestUser;
import com.bv.assignment.leave_request_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/users";
    }

    @Test
    void testCreateUser() {
        RequestUser user = new RequestUser();
        user.setUsername("john");
        user.setPassword("john123");
        user.setRole(RequestUser.Role.USER);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<RequestUser> entity = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    void testGetAllUsers() {
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class);
        assertEquals(401, response.getStatusCodeValue());
    }
}
