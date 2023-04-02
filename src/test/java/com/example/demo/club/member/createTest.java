package com.example.demo.club.member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.club.domain.*;
import com.example.demo.club.repository.*;
import com.example.demo.club.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.web.WebAppConfiguration;

@Slf4j
@WebAppConfiguration
@SpringBootTest
public class createTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private CdGrpRepository cdGrpRepository;
    @Autowired
    private CdRepository cdRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberService memberService;

    @Test
    public void createMember(){
        Member mem = new Member();
        Member mem2 = new Member();
        List<Member>members = new ArrayList<>();
    	List<Club>clubs = new ArrayList<>();
    	ClubInfo clubInfo = new ClubInfo();
    	LocalDateTime currentDate = LocalDateTime.now();
    	List<File> files = new ArrayList<>();

        String [][] fileAtb = {{"soccer.jpg","/images/club/soccer.jpg", "jpg"}
                            ,{"baseball.jpg","/images/club/baseball.jpg", "jpg"}
                            ,{null,null,null}};
        String [][] clubAtb = {{"100101", "200101", "축구동아리","101","저희는 서울대학교를 대표하는 축구동아리 사커스입니다."}
                            ,{"100101", "200101", "농구동아리","102","저희는 서울대학교의 대표 농구동아리 비스킷입니다."}
                            ,{"100101", "200102", "공예동아리","103","저희는 예술을 좋아하는 동아리입니다."}};

        String password = passwordEncoder.encode("1234");
        String password2 = passwordEncoder.encode("1234");
        mem.createMember("sjmoon","문승주", password, "", Role.USER);
        members.add(mem);
        mem2.createMember("phg","박형근", password2, "", Role.USER);
        members.add(mem2);

        for(int i = 0; i < 3; i++){
            File file = createFile(fileAtb[i][0], fileAtb[i][1], fileAtb[i][2]);
            if (i < 2) {
                files.add(file);
            }else{
                file = null;
            }

            Club club = createClub(clubAtb[i][0], clubAtb[i][1], clubAtb[i][2],clubAtb[i][3],clubAtb[i][4],file);
            if (i < 2){
                clubInfo = new ClubInfo();
                clubInfo.createClubInfo(club, mem, currentDate);
                club.getClubInfoList().add(clubInfo);
                clubInfo = new ClubInfo();
                clubInfo.createClubInfo(club, mem2, currentDate);
                club.getClubInfoList().add(clubInfo);
            }
            clubs.add(club);
        }

        memberRepository.saveAll(members);
    	fileRepository.saveAll(files);
        clubRepository.saveAll(clubs);
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

    @Test
    public void createCode() {
        CdGrp cdGrp = new CdGrp();
        Cd cd = new Cd();
        List<Cd> list = new ArrayList<>();
        cdGrp.createCdGrp("0101","동아리종류");
        cd.createCd("200101","2001","운동");
        list.add(cd);
        cd = new Cd();
        cd.createCd("200102","2001","미술");
        list.add(cd);
        cd = new Cd();
        cd.createCd("200103","2001","음악");
        list.add(cd);
        cdGrpRepository.save(cdGrp);
        cdRepository.saveAll(list);
    }
}
