package com.solutionladder.ethearts.contoller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.solutionladder.ethearts.model.response.GenericResponse;

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
}
