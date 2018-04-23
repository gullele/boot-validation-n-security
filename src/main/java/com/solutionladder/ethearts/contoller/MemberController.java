package com.solutionladder.ethearts.contoller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.solutionladder.ethearts.model.request.AuthenticationRequest;
import com.solutionladder.ethearts.persistence.entity.Member;
import com.solutionladder.ethearts.persistence.repository.AuthenticationResponse;
import com.solutionladder.ethearts.security.TokenUtil;
import com.solutionladder.ethearts.service.MemberService;

/**
 * Controller class for handling members.
 * @author Kaleb Woldearegay <kaleb@solutionladder.com>
 * 
 * @todo Move the CORS implementation to its own config
 *
 */
@RestController
@RequestMapping(path="/member")
@CrossOrigin(origins = {"http://localhost:8080", "*"})
public class MemberController {

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(path= {"", "/"})
    public Iterable<Member> list() {
        return this.memberService.getAll();
    }

    /**
     * Update member
     * @param member
     * @return
     */
    @PutMapping(path= {"", "/"})
    public ResponseEntity<Member> edit(
            @Valid @RequestBody Member member) {
        this.memberService.save(member);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    /**
     * Create member.
     * @see 
     */
    @PostMapping(
            path= {"/", ""}, 
            consumes="application/json", 
            produces="application/json")
    public ResponseEntity<Member> create(
            @Valid @RequestBody Member member) {
        this.memberService.save(member);
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    /**
     * 
     * @return
     */
    @PostMapping(
            path= {"/login","/login/"},
            consumes="application/json",
            produces="application/json"
            )
    public ResponseEntity<Member> login(
            @Valid @RequestBody AuthenticationRequest loginRequest) {
        Member member = this.memberService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if (member == null) {
            return new ResponseEntity<>(member, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @DeleteMapping(
            path= {"/logout","/logout/"})
    public void exit() {
        //apply
    }

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService customUserDetailsService;

    @RequestMapping(value = "/authenticate", method = { RequestMethod.POST })
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest) {

        try {
            String username = authenticationRequest.getEmail();
            String password = authenticationRequest.getPassword();

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = this.authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

            List<String> roles = new ArrayList<>();

            for (GrantedAuthority authority : userDetails.getAuthorities()) {
                roles.add(authority.toString());
            }

            return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(userDetails.getUsername(), roles,
                    TokenUtil.createToken(userDetails), HttpStatus.OK), HttpStatus.OK);

        } catch (BadCredentialsException bce) {
            return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(), HttpStatus.UNPROCESSABLE_ENTITY);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Just for testing the security
     * @return
     */
    @RequestMapping(value ="/membercities")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_USER')")
    public List<String> getMemberCities(){
        List<String> cities = new ArrayList<>();
        cities.add("Addis ababa");
        cities.add("New York");
        cities.add("Brussels");

        return cities;
    }
    
    /**
     * Just for testing the security to be moved to testing
     * @return
     */
    @RequestMapping(value ="/allmembers")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR_NON_EXISTING')")
    public List<String> getMembers(){
        List<String> cities = new ArrayList<>();
        cities.add("Addis Ababa");
        cities.add("New York");
        cities.add("Brussels");

        return cities;
    }
}