package com.example.demo.club.controller;

import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.dto.TokenDto;
import com.example.demo.club.service.*;
import org.springframework.http.HttpHeaders;
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
	public ResponseEntity<String> login(@RequestBody MemberDTO member, HttpServletResponse response, ModelMap model) throws Exception {
		ResponseEntity<TokenDto> tokenDtoResponseEntity = memberService.signIn(member);
		/*
		Cookie cookie = new Cookie(
				"accessToken",
				tokenDtoResponseEntity.getBody().getAccessToken()
		);

		cookie.setPath("/");
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);*/

		// Return the JWT token in the response body
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", tokenDtoResponseEntity.getBody().getAccessToken());
		return ResponseEntity.ok().headers(headers).body("You are logged in!");


		// JWT를 HTTP Header에 추가
		//response.setHeader("Authorization", tokenDtoResponseEntity.getHeaders().getFirst("Authorization") );

		// 다른 URL로 Redirect
		//response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		//response.setHeader("Location", "/member/main");

		//return "redirect:/member/main";
	}

	@RequestMapping("/join")
	@ResponseBody
	public void join(Member member) {
		memberService.createMember(member);
	}

}
