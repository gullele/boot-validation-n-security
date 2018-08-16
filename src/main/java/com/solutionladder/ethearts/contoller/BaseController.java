package com.solutionladder.ethearts.contoller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.solutionladder.ethearts.model.response.GenericResponse;
import com.solutionladder.ethearts.persistence.entity.Member;

/**
 * Abstract base controller for controllers
 * @author Kaleb Woldearegay <kaleb@solutionladder.com>
 *
 */
public abstract class BaseController {
    
    /**
     * When there is error, response the error with common format
     * @param messages
     * @param status
     * @return
     */
    protected ResponseEntity<GenericResponse> responseError(List<String> messages, HttpStatus status) {
        GenericResponse response = new GenericResponse();
        response.setMessage(messages);
        return new ResponseEntity<>(response, status);
    }
    
    /**
     * For entities with validation, when the validation failed, this will kick in
     * @param bindingResult
     * @return
     */
    protected ResponseEntity<GenericResponse> checkValidationErrors(BindingResult bindingResult) {
        List<String> messages = new ArrayList<>();
        for (ObjectError error : bindingResult.getAllErrors()) {
           messages.add(error.getDefaultMessage());
        }
        return this.responseError(messages, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Get logged member from token. Basically the token will contain lots of information about the 
     * logged user and one of the info will be mebmerId, username and maybe email
     * The member provided by this class does not contain full member information
     * 
     * @return
     */
    protected Member getLoggedMemberId() {
        return null;
    }
}
