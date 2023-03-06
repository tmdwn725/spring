package com.example.demo.club;

import com.example.demo.club.domain.Club;
import com.example.demo.club.repository.ClubRepository;
import com.example.demo.club.service.ClubService;
import com.example.demo.club.service.MemberService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class ClubTest {

    ClubRepository clubRepository;
    ClubService clubService;
    MemberService memberService;


    @Rollback(false)
    public void createClub(Club club) {

    }

}
