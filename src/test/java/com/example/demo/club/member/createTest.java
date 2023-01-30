package com.example.demo.club.member;

import com.example.demo.club.domain.Role;
import com.example.demo.club.domain.member;
import com.example.demo.club.dto.MemberDTO;
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
        member mem = new member();
        mem.createMember("test", "1234", "", Role.USER);
        userRepository.save(mem);

    }
}
