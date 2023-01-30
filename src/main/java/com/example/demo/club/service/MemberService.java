package com.example.demo.club.service;

import com.example.demo.club.config.SecurityConfig;
import com.example.demo.club.domain.Member;
import com.example.demo.club.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public String createMember(Member member) {
        member = userRepository.save(member);
        return member.getMemberId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = userRepository.findByMemberId(username)
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자 입니다."));


//        new SecurityConfig().getPasswordEncoder().matches()

        return User.builder()
                .username(member.getMemberId())
                .password(getEncodedPassword(member.getPassword()))
                .roles("USER")
                .build();
    }

    private String getEncodedPassword(String password) {
        return ("{noop}" + password);
    }

}
