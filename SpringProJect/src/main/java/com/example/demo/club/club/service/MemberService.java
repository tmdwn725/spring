package com.example.demo.club.club.service;

import com.example.demo.club.domain.MEMBER;
import com.example.demo.club.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public String createMember(MEMBER member) {
        member = userRepository.save(member);
        return member.getUserId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MEMBER member = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not found user"));

        return User.builder()
                .username(member.getUserId())
                .password(getEncodedPassword(member.getUserPassword()))
                .authorities(member.getAuthority())
                .build();
    }

    private String getEncodedPassword(String password) {
        return ("{noop}" + password);
    }

}
