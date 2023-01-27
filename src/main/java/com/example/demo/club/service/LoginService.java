package com.example.demo.club.service;

import com.example.demo.club.domain.member;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
	@Autowired
    private UserRepository userRepository;

    public Optional<member> findUser(MemberDTO memberDTO){
        Optional<member> user = userRepository.findByMemberId(memberDTO.getMemberId());
        return user;
    }
}
