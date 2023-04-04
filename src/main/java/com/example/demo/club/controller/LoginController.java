package com.example.demo.club.controller;

import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.dto.TokenDto;
import com.example.demo.club.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.club.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

	private final LoginService loginService;
	private final MemberService memberService;
	private final ClubService clubService;
	private final ClubInfoService clubInfoService;
	private final CdService cdService;


	@GetMapping("/login")
	public String login(@RequestParam(value = "fail", required = false) String flag, Model model) {
		model.addAttribute("failed", flag != null);
		return "member/login";
	}

	@PostMapping("/login")
	public String login(@Validated MemberDTO member, HttpServletResponse res, ModelMap model) throws Exception {
		ResponseEntity<TokenDto> tokenDtoResponseEntity = memberService.signIn(member);
		Cookie cookie = new Cookie(
				"accessToken",
				tokenDtoResponseEntity.getBody().getAccessToken()
		);

		cookie.setPath("/");
		cookie.setMaxAge(Integer.MAX_VALUE);

		res.addCookie(cookie);

		model.addAttribute("userName", member.getMemberId());
		model.addAttribute("clubList",clubService.selectClubList());
		member = memberService.selectMemberById(member.getMemberId());
		model.addAttribute("member", member);
		model.addAttribute("myClubList",clubInfoService.selectMyClubList(member));
		model.addAttribute("clubCdList", cdService.getClubCd());

		return "redirect:/member/main";
	}

	@RequestMapping("/join")
	@ResponseBody
	public void join(Member member) {
		memberService.createMember(member);
	}

}
