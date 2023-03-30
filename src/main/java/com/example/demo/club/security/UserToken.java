package com.example.demo.club.security;

import com.example.demo.club.dto.PrincipalDetails;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserToken extends UsernamePasswordAuthenticationToken {

    @Autowired
    PrincipalDetails user;

    public UserToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    @Override
    public Object getDetails() {
        return super.getDetails();
    }
}
