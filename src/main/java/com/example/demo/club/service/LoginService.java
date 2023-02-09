package com.example.demo.club.service;

import com.example.demo.club.domain.Member;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Optional<Member> findUser(MemberDTO memberDTO){
        Optional<Member> user = memberRepository.findByMemberId(memberDTO.getMemberId());
        return user;
    }

}
