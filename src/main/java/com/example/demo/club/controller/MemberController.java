package com.example.demo.club.controller;

import com.example.demo.club.service.ClubService;
import com.example.demo.club.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private ClubService clubService;

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("userName", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("clubList",clubService.selectClubList());
        return "main/daygrid-views.html";
    }

}
