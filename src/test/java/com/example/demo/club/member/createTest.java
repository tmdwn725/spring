package com.example.demo.club.member;

import com.example.demo.club.config.SecurityConfig;
import com.example.demo.club.domain.Role;
import com.example.demo.club.domain.Member;
import com.example.demo.club.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class createTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createMember(){
        Member mem = new Member();
        String password = new SecurityConfig().getPasswordEncoder().encode("123");
        mem.createMember("test", password, "", Role.USER);
        userRepository.save(mem);
    }

}
