package com.example.demo.club.controller;

import com.example.demo.club.common.CookieUtil;
import com.example.demo.club.dto.ClubInfoDTO;
import com.example.demo.club.dto.ScheduleDTO;
import com.example.demo.club.security.jwt.JwtTokenProvider;
import com.example.demo.club.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.club.dto.ClubDTO;
import com.example.demo.club.dto.MemberDTO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

	private final ClubService clubService;
	private final ClubInfoService clubInfoService;
	private final MemberService memberService;
	private final ScheduleService scheduleService;
	private final CdService cdService;
	private final JwtTokenProvider jwtTokenProvider;

	
	@GetMapping("/myClub")
	public String getClub(HttpServletRequest request, Model model, ClubDTO cDTO) {
		ClubDTO club = clubService.selectClub(cDTO.getClubSeq());
		String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
		MemberDTO member = memberService.selectMemberById(memberId);
		ClubInfoDTO myClubInfo =club.getClubInfoList().stream()
				.filter(p -> p.getMember().getMemberSeq()==member.getMemberSeq())
				.findFirst()
				.orElse(null);
		model.addAttribute("userName", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("member",  member);
		model.addAttribute("myClubList",clubInfoService.selectMyClubList(member));
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

	@GetMapping("/getClubSchedule/{date}")
	public ResponseEntity<List<ScheduleDTO>> getClubSchedule(@PathVariable(required = false) String date, Model model){
		LocalDateTime stDt = LocalDateTime.parse(date + "T00:00:00");
		List<ScheduleDTO> list = scheduleService.findClubScheduleList(1L,stDt,stDt.plusDays(1));
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
