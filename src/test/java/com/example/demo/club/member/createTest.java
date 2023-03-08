package com.example.demo.club.member;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.ClubInfo;
import com.example.demo.club.domain.File;
import com.example.demo.club.domain.Member;
import com.example.demo.club.domain.Role;
import com.example.demo.club.domain.School;
import com.example.demo.club.repository.ClubInfoRepository;
import com.example.demo.club.repository.ClubRepository;
import com.example.demo.club.repository.FileRepository;
import com.example.demo.club.repository.MemberRepository;
import com.example.demo.club.repository.SchoolRepository;
import com.example.demo.club.security.SecurityConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class createTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubInfoRepository clubinfoRepository;

    @Autowired
    private SchoolRepository schoolRepository;
    
    @Autowired
    private FileRepository fileRepository;

    @Test
    @Rollback(value = false)
    void onlyMemberCreate() {
        String password = new SecurityConfig().getPasswordEncoder().encode("1234");
        Member member = new Member();
        member.createMember("phg", password, "", Role.USER);
        memberRepository.save(member);
    }

    @Test
    @Rollback(value = false)
    void onlyClubCreate() {
        Optional<Member> member = memberRepository.findByMemberId("phg");
        Club club = clubRepository.findByClubNm("축구동아리");
        member.ifPresentOrElse(value -> {
            ClubInfo clubInfo = new ClubInfo();
            clubInfo.createClubInfo(club,value,LocalDateTime.now());
            clubinfoRepository.save(clubInfo);
        }, () -> log.error("존재하지 않는 계정!"));
    }

    @Test
    public void createMember(){
        Member mem = new Member();
        Club club = new Club();
        File fl = new File();
    	List<Club>clubs = new ArrayList<>();
    	ClubInfo clubInfo = new ClubInfo();
    	List<ClubInfo>clubInfos = new ArrayList<>();
    	LocalDateTime currentDate = LocalDateTime.now();
    	List<File> files = new ArrayList<>();


        String password = new SecurityConfig().getPasswordEncoder().encode("1234");
        mem.createMember("sjmoon", password, "", Role.USER);
       
    	fl.createFile("soccer.jpg","/images/club/soccer.jpg", "jpg");
    	files.add(fl);

        club.createClub("100101", "200101", "축구동아리","101","저희는 서울대학교를 대표하는 축구동아리 사커스입니다.",fl);
    	clubs.add(club);

    	clubInfo.createClubInfo(club, mem, currentDate);
    	clubInfos.add(clubInfo);

    	fl = new File();
    	fl.createFile("baseball.jpg","/images/club/baseball.jpg", "jpg");
    	files.add(fl);
    	
    	club = new Club();
    	club.createClub("100101", "200101", "농구동아리","102","저희는 서울대학교의 대표 농구동아리 비스킷입니다.",fl);
    	clubs.add(club);

    	clubInfo = new ClubInfo();
    	clubInfo.createClubInfo(club, mem, currentDate);
    	clubInfos.add(clubInfo);

    	club = new Club();
    	club.createClub("100101", "200102", "공예동아리","103","저희는 예술을 좋아하는 동아리입니다.",null);
    	clubs.add(club);

    	fileRepository.saveAll(files);
    	memberRepository.save(mem);
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
