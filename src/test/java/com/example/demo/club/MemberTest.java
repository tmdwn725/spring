package com.example.demo.club;

import com.example.demo.club.domain.Role;
import com.example.demo.club.domain.Member;
import com.example.demo.club.repository.MemberRepository;
import com.example.demo.club.security.SecurityConfig;
import com.example.demo.club.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @Test
    @Rollback(value = false)
    public void createMember(){
        String password = new SecurityConfig().getPasswordEncoder().encode("1234");
        Member member = new Member();
        member.createMember("phg", password, "", Role.USER);
        memberRepository.save(member);
    }

    public void findAll(){
        List<Member> list = memberService.findAll();

        for (Member mem : list) {
            System.out.println(mem.getMemberId());
        }
    }

}
