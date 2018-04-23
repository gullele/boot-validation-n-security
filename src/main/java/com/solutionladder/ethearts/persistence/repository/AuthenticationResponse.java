package com.solutionladder.ethearts.persistence.repository;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

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
  private HttpStatus status;

  public AuthenticationResponse(String email, List<String> roles, String token, HttpStatus status) {
      this.roles = roles;
      this.token = token;
      this.email = email;
      this.status = status;
  }

  public AuthenticationResponse() {
      this.token = "";
      this.email = "";
      this.roles = Collections.emptyList();
      this.status = HttpStatus.NOT_FOUND;
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

  public HttpStatus getStatus() {
      return this.status;
  }
}