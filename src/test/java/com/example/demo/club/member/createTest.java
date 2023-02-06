package com.example.demo.club.member;


import com.example.demo.club.config.SecurityConfig;
import com.example.demo.club.domain.Role;
import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.Member;
import com.example.demo.club.repository.ClubRepository;
import com.example.demo.club.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class createTest {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ClubRepository clubRepository;

    @Test
    public void createMember(){
        Member mem = new Member();
        String password = new SecurityConfig().getPasswordEncoder().encode("1234");
        mem.createMember("sjmoon", password, "", Role.USER);
        userRepository.save(mem);
    }
    
    @Test
    public void createClub(){
    	Club club = new Club();
    	List<Club>clubs = new ArrayList<>();
    	club.createClub("100101", "200101", "축구동아리", "101");
    	clubs.add(club);
    	club.createClub("100101", "200101", "농구동아리", "102");
    	clubs.add(club);
        clubRepository.saveAll(clubs);
    }
}
