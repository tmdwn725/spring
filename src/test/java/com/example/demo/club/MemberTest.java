package com.example.demo.club;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.example.demo.club.domain.Member;
import com.example.demo.club.domain.Role;
import com.example.demo.club.repository.MemberRepository;
import com.example.demo.club.security.SecurityConfig;
import com.example.demo.club.service.MemberService;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
public class MemberTest {
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MemberService memberService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	@Rollback(value = false)
	public void createMember() {
		String password = passwordEncoder.encode("1234");
		Member member = new Member();
		member.createMember("admin","관리자", password, "1234", Role.ADMIN);
		memberRepository.save(member);
	}

	public void findAll() {
		List<Member> list = memberService.findAll();

		for (Member mem : list) {
			System.out.println(mem.getMemberId());
		}
	}

}
