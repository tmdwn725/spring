package com.example.demo.club.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {
    private String name;
    private String email;
    private String picture;
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public void update(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }

}
