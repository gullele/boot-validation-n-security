package com.solutionladder.ethearts.persistence.repository;

import java.util.Collections;
import java.util.List;

/**
 * Response for authentication
 * 
 * @author Kaleb Woldearegay <kaleb@solutionladder.com>
 *
 */
public class AuthenticationResponse {
    private String email;
    private List<String> roles;
    private String token;

    public AuthenticationResponse(String email, List<String> roles, String token) {
        this.roles = roles;
        this.token = token;
        this.email = email;
    }

    public AuthenticationResponse() {
        this.token = "";
        this.email = "";
        this.roles = Collections.emptyList();
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public String getToken() {
        return this.token;
    }

    public String getEmail() {
        return this.email;
    }
}