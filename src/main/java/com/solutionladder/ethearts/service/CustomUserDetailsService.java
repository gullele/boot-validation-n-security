package com.solutionladder.ethearts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solutionladder.ethearts.persistence.entity.Member;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

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

    private List<GrantedAuthority> getGrantedAuthorities(Member member) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        member.getRoles().forEach(role -> { 
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        
        return authorities;
    }
}