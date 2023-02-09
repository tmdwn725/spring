package com.example.demo.club.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private String memberId;
    private String password;

    public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String memberId, String password) {
        super(authorities);
        this.memberId = memberId;
        this.password = password;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getPrincipal() {
        return this.memberId;
    }
}
