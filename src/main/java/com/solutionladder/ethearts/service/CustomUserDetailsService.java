package com.solutionladder.ethearts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solutionladder.ethearts.persistence.entity.Member;
import com.solutionladder.ethearts.persistence.repository.AuthenticationResponse;
import com.solutionladder.ethearts.security.TokenUtil;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if (email.trim().isEmpty()) {
            throw new UsernameNotFoundException("Email not provided");
        }

        Optional<Member> member = this.memberService.findByEmail(email);

        if (!member.isPresent()) {
            throw new UsernameNotFoundException("Email " + email + " not found");
        }

        return new org.springframework.security.core.userdetails.User(member.get().getEmail(), member.get().getPasswordHash(),
                getGrantedAuthorities(member.get()));
    }
    
    public AuthenticationResponse authenticate (String username, String password) throws BadCredentialsException {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = this.loadUserByUsername(username);

        List<String> roles = new ArrayList<>();

        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.add(authority.toString());
        }

        return new AuthenticationResponse(userDetails.getUsername(), roles,
                TokenUtil.createToken(userDetails));
    }

    private List<GrantedAuthority> getGrantedAuthorities(Member member) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        member.getRoles().forEach(role -> { 
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        
        return authorities;
    }
}