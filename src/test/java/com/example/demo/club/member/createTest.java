package com.example.demo.club.member;


import com.example.demo.club.config.SecurityConfig;
import com.example.demo.club.domain.Role;
import com.example.demo.club.domain.School;
import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.ClubInfo;
import com.example.demo.club.domain.Member;
import com.example.demo.club.repository.ClubInfoRepository;
import com.example.demo.club.repository.ClubRepository;
import com.example.demo.club.repository.MemberRepository;
import com.example.demo.club.repository.SchoolRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class createTest {

    @Autowired
    private MemberRepository userRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubInfoRepository clubinfoRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    public void createMember(){
        Member mem = new Member();
        Club club = new Club();
    	List<Club>clubs = new ArrayList<>();
    	ClubInfo clubInfo = new ClubInfo();
    	List<ClubInfo>clubInfos = new ArrayList<>();
    	LocalDate currentDate = LocalDate.now();


        String password = new SecurityConfig().getPasswordEncoder().encode("1234");
        mem.createMember("sjmoon", password, "", Role.USER);


        club.createClub("100101", "200101", "축구동아리", "101");
    	clubs.add(club);

    	clubInfo.createClubInfo(club, mem, currentDate);
    	clubInfos.add(clubInfo);

    	club = new Club();
    	club.createClub("100101", "200101", "농구동아리", "102");
    	clubs.add(club);

    	clubInfo = new ClubInfo();
    	clubInfo.createClubInfo(club, mem, currentDate);
    	clubInfos.add(clubInfo);

    	club = new Club();
    	club.createClub("100101", "200102", "공예동아리", "103");
    	clubs.add(club);

    	userRepository.save(mem);
        clubRepository.saveAll(clubs);
        clubinfoRepository.saveAll(clubInfos);

    }

    @Test
    public void createSchool() {
    	School sc = new School();
    	sc.createSchool("100101","서울대학교", "서울특별시 관악구", "02-1111-1111");
    	schoolRepository.save(sc);
    }
}
