package com.example.demo.club.config;

import com.example.demo.club.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private MemberService memberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName().toString();
        String password = authentication.getCredentials().toString();

        UserDetails user = memberService.loadUserByUsername(username);
        Boolean checkPassword = new SecurityConfig().getPasswordEncoder().matches(password,user.getPassword());

        if(user == null) {
            new UsernameNotFoundException("사용자 찾을 수 없음");
        }

        if(checkPassword != true) {
            new BadCredentialsException("패스워드가 일치하지 않음");
        }

        return new CustomAuthenticationToken(user.getAuthorities(),username,password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //supports 이후 authenticate
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
