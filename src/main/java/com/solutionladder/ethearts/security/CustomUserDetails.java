package com.solutionladder.ethearts.security;

import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.solutionladder.ethearts.persistence.entity.Member;
import com.solutionladder.ethearts.persistence.repository.MemberRepository;

/**
 * @modified code
 *
 */
@Service
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Optional<Member> member = memberRepository.findByEmail(email);
            if (!member.isPresent()) {
                throw new UsernameNotFoundException("User with email '" + email + "' not found");
            }

            Member user = member.get();

            Hibernate.initialize(user.getRoles());
            UserDetails detail = org.springframework.security.core.userdetails.User
                    .withUsername(email)
                    .password(user.getPasswordHash())
                    .authorities(user.getRoles())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
            return detail;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}