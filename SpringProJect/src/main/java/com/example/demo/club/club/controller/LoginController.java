package com.example.demo.club.club.controller;

import com.example.demo.club.domain.MEMBER;
import com.example.demo.club.service.LoginService;
import com.example.demo.club.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

	@Autowired private LoginService loginService;
	@Autowired private MemberService memberService;


	@RequestMapping("/login")
	public String findUser(Model model){
		model.addAttribute("user",SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		 UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication();
		 log.debug(user.getUsername());

		return "/user/login";
	}

	@RequestMapping("/join")
	@ResponseBody
	public void join(MEMBER member) {
		memberService.createMember(member);
	}

}
