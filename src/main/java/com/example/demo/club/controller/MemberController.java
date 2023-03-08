package com.example.demo.club.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.service.ClubInfoService;
import com.example.demo.club.service.ClubService;
import com.example.demo.club.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ClubService clubService;
    private final ClubInfoService clubInfoService;

    @GetMapping("/mypage/{memberSeq}")
    public String mypage(@PathVariable(required = false) Long memberSeq, Model model) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("userName", userId);
        MemberDTO member = memberService.selectMemberBySeq(memberSeq);
        model.addAttribute("member",member);
        model.addAttribute("clubList",clubService.selectClubList());
        model.addAttribute("myClubList",clubInfoService.selectMyClubList(member));
        return "member/mypage";
    }

    @GetMapping("/main")
    public String main(Model model) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("userName", userId);
        model.addAttribute("clubList",clubService.selectClubList());
        MemberDTO member = memberService.selectMemberById(userId);
        model.addAttribute("member", member);
        model.addAttribute("myClubList",clubInfoService.selectMyClubList(member));
        return "main/main";
    }

}
