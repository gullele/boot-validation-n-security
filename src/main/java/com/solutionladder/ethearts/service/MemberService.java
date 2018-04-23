package com.solutionladder.ethearts.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.solutionladder.ethearts.persistence.entity.Member;
import com.solutionladder.ethearts.persistence.repository.MemberRepository;

/**
 * Service class for member
 * @author Kaleb Woldearegay <kaleb@solutionladder.com>
 *
 */
@Service
//public class MemberService implements UserDetailsService {
public class MemberService {

    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    @Autowired
    public MemberRepository memberRepository;
    
    public Member save(Member member) {
        member.setPasswordHash(this.bcryptPasswordEncoder.encode(member.getPassword()));
        this.memberRepository.save(member);
        return member;
    }

    public Iterable<Member> getAll() {
        return this.memberRepository.findAll();
    }
    
    public Member get(Long id) {
        Optional<Member> member = this.memberRepository.findById(id);
        if (member.isPresent()) return member.get(); 
        return null;
    }

    public Optional<Member> findByEmail(String email) {
        return this.memberRepository.findByEmail(email);
    }
    
    public Member findByEmailAndPassword(String email, String password) {
        String passwordHash = "hashed";
        Optional<Member> member = this.memberRepository.findByEmailAndPasswordHash(email, passwordHash);
        if (member.isPresent()) return member.get();
        
        return null;
    }
}
