package com.example.demo.club.controller;

import com.example.demo.club.common.CookieUtil;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.dto.TokenDTO;
import com.example.demo.club.exception.CustomException;
import com.example.demo.club.service.*;
import org.springframework.http.HttpStatus;
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
	private final CookieUtil cookieUtil;

	@GetMapping("/login")
	public String login(@RequestParam(value = "fail", required = false) String flag, Model model) {
		model.addAttribute("failed", flag != null);
		return "member/login";
	}

	@PostMapping("/login")
	public String login(@Validated MemberDTO member, HttpServletResponse response, ModelMap model) throws Exception {
		try{
			ResponseEntity<TokenDTO> tokenDtoResponseEntity = memberService.signIn(member);
			Cookie cookie = cookieUtil.createCookie("accessToken",tokenDtoResponseEntity.getBody().getAccessToken());
			response.addCookie(cookie);
		}catch (CustomException e){
			model.addAttribute("message", e.getMessage());
			return "member/login";
		}
		return "redirect:/member/main";
	}

	@GetMapping("/logout/{memberId}")
	public String logout(@RequestParam(value = "fail", required = false) String memberId, HttpServletRequest request) {
		//return ResponseEntity.ok(memberService.signout(request, memberId));
		memberService.signout(request, memberId);
		return "redirect:/login";
	}

	@RequestMapping("/join")
	@ResponseBody
	public void join(Member member) {
		memberService.createMember(member);
	}

	@GetMapping("/oauth")
	public String oauth() {
		return "member/oauth";
	}

}
