package com.example.demo.club.controller;

import com.example.demo.club.dto.ClubInfoDTO;
import com.example.demo.club.service.CdService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.club.dto.ClubDTO;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.service.ClubInfoService;
import com.example.demo.club.service.ClubService;
import com.example.demo.club.service.MemberService;

@Controller
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

	private final ClubService clubService;

	private final ClubInfoService clubInfoService;

	private final MemberService memberService;

	private final CdService cdService;
	
	@GetMapping("/myClub")
	public String getClub(Model model,ClubDTO cDTO,MemberDTO mDTO) {
		ClubDTO club = clubService.selectClub(cDTO.getClubSeq());
		ClubInfoDTO myClubInfo =club.getClubInfoList().stream()
				.filter(p -> p.getMember().getMemberSeq()==mDTO.getMemberSeq())
				.findFirst()
				.orElse(null);
		model.addAttribute("userName", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("member",  memberService.selectMemberBySeq(mDTO.getMemberSeq()));
		model.addAttribute("myClubList",clubInfoService.selectMyClubList(mDTO));
		model.addAttribute("myClubInfo",myClubInfo);
		model.addAttribute("club",club);
		model.addAttribute("clubCdList", cdService.getClubCd());
		return "club/club";
	}

	@PostMapping("/applyClub")
	public ResponseEntity<Void> applyClub(Model model, ClubDTO club){
		MemberDTO member = memberService.selectMemberById(SecurityContextHolder.getContext().getAuthentication().getName());
		clubService.createClub(club, member);
		return ResponseEntity.ok().build();
	}

}
