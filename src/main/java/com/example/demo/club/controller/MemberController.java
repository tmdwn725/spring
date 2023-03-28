package com.example.demo.club.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.service.ClubInfoService;
import com.example.demo.club.service.ClubService;
import com.example.demo.club.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.Map;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final ClubService clubService;
    private final ClubInfoService clubInfoService;
    private final CdService cdService;

    @GetMapping("/main")
    public String main(Model model) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("userName", userId);
         model.addAttribute("clubList",clubService.selectClubList());
        MemberDTO member = memberService.selectMemberById(userId);
        model.addAttribute("member", member);
        model.addAttribute("myClubList",clubInfoService.selectMyClubList(member));
        model.addAttribute("clubCdList", cdService.getClubCd());
        return "main/main";
    }

    /** 마이페이지 **/

    /* 내 프로필 화면 */
    @GetMapping("/mypage/{memberSeq}")
    public String mypage(@PathVariable(required = false) Long memberSeq, Model model) {
        MemberDTO member = memberService.selectMemberBySeq(memberSeq);
        model.addAttribute("member",member);
        model.addAttribute("myClubList",clubInfoService.selectMyClubList(member));
        return "member/mypage";
    }

    /* 패스워드 변경 화면 */
    @GetMapping("/mypage/updatePassword/{memberSeq}")
    public String updatePasswordForm(@PathVariable(required = false) Long memberSeq, Model model) {
        MemberDTO member = memberService.selectMemberBySeq(memberSeq);
        model.addAttribute("member",member);
        model.addAttribute("myClubList",clubInfoService.selectMyClubList(member));
        return "member/updatePassword";
    }

    /* 패스워드 변경 로직 */
    @PostMapping("/mypage/updatePassword/{memberSeq}")
    public String updatePassword(@PathVariable(required = false) Long memberSeq, Model model,
                                 @Validated @ModelAttribute PasswordDTO passwordDTO, Errors errors) {
        if (errors.hasErrors()) {
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            MemberDTO member = memberService.selectMemberBySeq(memberSeq);
            model.addAttribute("member",member);
//            model.addAttribute("memberSeq", memberSeq);
            return "member/updatePassword";
        } else {
            MemberDTO member = memberService.selectMemberBySeq(memberSeq);
            model.addAttribute("member",member);
            model.addAttribute("myClubList",clubInfoService.selectMyClubList(member));
            return "member/main";
        }
    }

}
