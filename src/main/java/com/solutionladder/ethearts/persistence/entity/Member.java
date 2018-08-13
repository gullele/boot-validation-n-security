package com.solutionladder.ethearts.persistence.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.solutionladder.ethearts.model.errorhandler.MinimumAge;
import com.solutionladder.ethearts.model.errorhandler.UniqueEmail;

/**
 * Member persistence model
 * 
 * @author Kaleb Woldearegay <kaleb@solutionladder.com>
 *
 */
@Entity
public class Member extends DatedEntity{

    @NotEmpty(message = "First name cannot be empty")
    @Size(min = 2, max = 30)
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    @Email
    @Column(unique = true)
    @UniqueEmail
    private String email;

    @Transient
    @NotEmpty(message = "Password cannot be empty")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    private String passwordHash;

    @MinimumAge(value = 18)
    private LocalDate dateOfBirth;

    @ManyToMany(cascade = CascadeType.DETACH)
    private List<Role> roles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(role);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}