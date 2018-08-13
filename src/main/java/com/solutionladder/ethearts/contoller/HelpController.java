package com.solutionladder.ethearts.contoller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutionladder.ethearts.model.response.GenericResponse;
import com.solutionladder.ethearts.persistence.entity.Contribution;
import com.solutionladder.ethearts.persistence.entity.Help;
import com.solutionladder.ethearts.persistence.entity.HelpType;
import com.solutionladder.ethearts.service.HelpService;

/**
 * Controller class for handling Help.
 * @author Kaleb Woldearegay <kaleb@solutionladder.com>
 * 
 * @todo Move the CORS implementation to its own config
 *
 */
@RestController
@RequestMapping(path="/help")
@CrossOrigin(origins = {"*"})
public class HelpController extends BaseController{

    @Autowired
    private HelpService helpService;

    @GetMapping(path= {"", "/"})
    public Iterable<Help> list() {
        return this.helpService.getAll();
    }

    /**
     * Update help
     * @param help
     * @return
     */
    @PutMapping(path= {"", "/"})
    public ResponseEntity<Help> edit(
            @Valid @RequestBody Help help) {
        this.helpService.save(help);
        return new ResponseEntity<>(help, HttpStatus.OK);
    }

    @PostMapping(
            path= {"/helptype", "/helptype/"},
            consumes="application/json",
            produces="application/json")
    public ResponseEntity<GenericResponse> addHelpType(
            @Valid @RequestBody HelpType helpType,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return this.checkValidationErrors(bindingResult);
        }
        
        this.helpService.save(helpType);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    /**
     * Adding contribution per help. Anyone can help to the posted help as contribution.
     * For this to work
     * 1. First use member/authenticate to get the token
     * 2. pass the token through header with key x-auth-token along with the contribute body
     * Sample: {"help":{"id":1}, "message":"I want to help in resource", "member":{"id":1}, "helpType":{"id":1}}
     * @param contribution
     * @param bindingResult
     * @return
     */
    @PostMapping(
            path= {"/support", "/support/"},
            consumes="application/json",
            produces="application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GenericResponse> addHelpContribution(
            @Valid @RequestBody Contribution contribution,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return this.checkValidationErrors(bindingResult);
        }
        
        this.helpService.saveContribution(contribution);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
    
    public ResponseEntity<GenericResponse> addHelpResource() {
        return null;
    }

    /**
     * Create help.
     * @see 
     */
    @PostMapping(
            path= {"/", ""}, 
            consumes="application/json", 
            produces="application/json")
    public ResponseEntity<Help> create(
            @Valid @RequestBody Help help) {
        this.helpService.save(help);
        return new ResponseEntity<>(help, HttpStatus.CREATED);
    }
}