package com.example.demo.club.controller;

import com.example.demo.club.domain.Member;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.club.service.ClubInfoService;
import com.example.demo.club.service.ClubService;
import com.example.demo.club.service.MemberService;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ClubService clubService;
    private final ClubInfoService clubInfoService;

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

    /** 마이페이지 **/

    /* 내 프로필 */
    @GetMapping("/mypage/{memberSeq}")
    public String mypage(@PathVariable(required = false) Long memberSeq, Model model) {
        MemberDTO member = memberService.selectMemberBySeq(memberSeq);
        model.addAttribute("member",member);
        model.addAttribute("myClubList",clubInfoService.selectMyClubList(member));
        return "member/mypage";
    }

    /* 패스워드 변경 */
    @GetMapping("/mypage/updatePassword/{memberSeq}")
    public String updatePassword(@PathVariable(required = false) Long memberSeq, Model model) {
        MemberDTO member = memberService.selectMemberBySeq(memberSeq);
        model.addAttribute("member",member);
        model.addAttribute("myClubList",clubInfoService.selectMyClubList(member));
        return "member/updatePassword";
    }

}
