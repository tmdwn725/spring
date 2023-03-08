package com.example.demo.club.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.club.domain.Member;
import com.example.demo.club.service.LoginService;
import com.example.demo.club.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

	private final LoginService loginService;
	private final MemberService memberService;


	@RequestMapping("/login")
	public String login(Model model){
		return "member/login";
	}

	@RequestMapping("/join")
	@ResponseBody
	public void join(Member member) {
		memberService.createMember(member);
	}

}
