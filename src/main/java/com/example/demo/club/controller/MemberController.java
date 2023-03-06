package com.example.demo.club.controller;

import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.service.ClubInfoService;
import com.example.demo.club.service.ClubService;
import com.example.demo.club.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.service.ClubInfoService;
import com.example.demo.club.service.ClubService;
import com.example.demo.club.service.MemberService;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private ClubService clubService;
    
    @Autowired
    private ClubInfoService clubInfoService;

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
