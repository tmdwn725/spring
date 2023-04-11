package com.example.demo.club.controller;

import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.dto.TokenDTO;
import com.example.demo.club.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.club.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
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
	public String login(@Validated MemberDTO member, HttpServletResponse response, ModelMap model) throws Exception {
		ResponseEntity<TokenDTO> tokenDtoResponseEntity = memberService.signIn(member);

		Cookie cookie = new Cookie(
				"accessToken",
				tokenDtoResponseEntity.getBody().getAccessToken()
		);

		cookie.setPath("/");
		cookie.setMaxAge(Integer.MAX_VALUE);
		cookie.setHttpOnly(true);
		//cookie.setSecure(true); //https
		response.addCookie(cookie);

		Cookie refreshCookie = new Cookie(
				"refreshToken",
				tokenDtoResponseEntity.getBody().getRefreshToken()
		);

		refreshCookie.setPath("/");
		refreshCookie.setMaxAge(Integer.MAX_VALUE);
		refreshCookie.setHttpOnly(true);
		response.addCookie(refreshCookie);

		return "redirect:/member/main";
	}

	@GetMapping("/logout")
	public ResponseEntity<String> logout(@AuthenticationPrincipal MemberDTO member, @RequestBody TokenDTO tokenDTO) {
		return ResponseEntity.ok(memberService.logout(tokenDTO.getAccessToken(), member));
	}

	@RequestMapping("/join")
	@ResponseBody
	public void join(Member member) {
		memberService.createMember(member);
	}

}
