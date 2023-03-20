package com.example.demo.club.member;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.club.domain.*;
import com.example.demo.club.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

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

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    @Rollback(value = false)
    public void onlyMemberCreate() {
        String password = new SecurityConfig().getPasswordEncoder().encode("1234");
        Member member = new Member();
        member.createMember("phg","박형근", password, "", Role.USER);
        memberRepository.save(member);
    }

    @Test
    @Rollback(value = false)
    public void onlyClubCreate() {
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
    	List<Club>clubs = new ArrayList<>();
    	ClubInfo clubInfo = new ClubInfo();
        ChatRoom chatRoom = new ChatRoom();
        List<ChatRoom> chatRooms = new ArrayList<>();
    	List<ClubInfo> clubInfos = new ArrayList<>();
    	LocalDateTime currentDate = LocalDateTime.now();
    	List<File> files = new ArrayList<>();
        String [][] fileAtb = {{"soccer.jpg","/images/club/soccer.jpg", "jpg"}
                            ,{"baseball.jpg","/images/club/baseball.jpg", "jpg"}
                            ,{null,null,null}};
        String [][] clubAtb = {{"100101", "200101", "축구동아리","101","저희는 서울대학교를 대표하는 축구동아리 사커스입니다."}
                            ,{"100101", "200101", "농구동아리","102","저희는 서울대학교의 대표 농구동아리 비스킷입니다."}
                            ,{"100101", "200102", "공예동아리","103","저희는 예술을 좋아하는 동아리입니다."}};

        String password = new SecurityConfig().getPasswordEncoder().encode("1234");
        mem.createMember("sjmoon","문승주", password, "", Role.USER);

        for(int i = 0; i < 3; i++){
            File file = createFile(fileAtb[i][0], fileAtb[i][1], fileAtb[i][2]);
            if (i < 2) {
                files.add(file);
            }else{
                file = null;
            }

            Club club = createClub(clubAtb[i][0], clubAtb[i][1], clubAtb[i][2],clubAtb[i][3],clubAtb[i][4],file);
            clubs.add(club);
            if (i < 2){
                clubInfo = new ClubInfo();
                clubInfo.createClubInfo(club, mem, currentDate);
                clubInfos.add(clubInfo);
            }
            chatRoom = new ChatRoom();
            chatRoom.createChatRoom(club,mem);
            chatRooms.add(chatRoom);
        }

    	fileRepository.saveAll(files);
    	memberRepository.save(mem);
        clubRepository.saveAll(clubs);
        clubinfoRepository.saveAll(clubInfos);
        chatRoomRepository.saveAll(chatRooms);
    }

    File createFile(String fileNm, String filePth, String fileExt){
        File file = new File();
        file.createFile(fileNm,filePth, fileExt);
        return file;
    }
    Club createClub(String schoolCd, String clubClsCd, String clubNm, String roomNm,String introduce,File file)  {
        Club club = new Club();
        club.createClub(schoolCd, clubClsCd, clubNm,roomNm,introduce,file);
        return club;
    }

    @Test
    public void createSchool() {
    	School sc = new School();
    	sc.createSchool("100101","서울대학교", "서울특별시 관악구", "02-1111-1111");
    	schoolRepository.save(sc);
    }
}
