package com.example.demo.club.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.club.dto.ClubDTO;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.service.ClubInfoService;
import com.example.demo.club.service.ClubService;
import com.example.demo.club.service.MemberService;

@Controller
@RequestMapping("/club")
public class ClubController {
	
	@Autowired
	ClubService clubService;

	@Autowired
	ClubInfoService clubInfoService;
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/myClub")
	public String getClub(Model model,ClubDTO cDTO,MemberDTO mDTO) {
		ClubDTO club = clubService.selectClub(cDTO.getClubSeq());
		model.addAttribute("userName", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("member",  memberService.selectMemberBySeq(mDTO.getMemberSeq()));
		model.addAttribute("myClubList",clubInfoService.selectMyClubList(mDTO));
		model.addAttribute("club",club);
		return "club/club";
	}

}
