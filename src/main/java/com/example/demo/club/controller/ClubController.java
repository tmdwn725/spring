package com.example.demo.club.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.club.dto.ClubDTO;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.service.ClubInfoService;
import com.example.demo.club.service.ClubService;

@Controller
@RequestMapping("/club")
public class ClubController {
	
	@Autowired
	ClubService clubService;

	@Autowired
	ClubInfoService clubInfoService;
	
	
	
	@RequestMapping("/myClub")
	public String getClub(Model model,ClubDTO dto,MemberDTO memDTO) {
		ClubDTO club = clubService.selectClub(dto.getClubSeq());
		model.addAttribute("userName", SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("myClubList",clubInfoService.selectMyClubList(memDTO));
		model.addAttribute("club",club);
		return "club/club";
	}

}
