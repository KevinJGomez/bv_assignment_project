package com.bv.assignment.leave_request_api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnTokenValidity {
    private String validToken;
    private String role;
    private String username;
}
