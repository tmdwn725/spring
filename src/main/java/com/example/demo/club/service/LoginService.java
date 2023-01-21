package com.example.demo.club.service;

import com.example.demo.club.domain.MEMBER;
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

    public Optional<MEMBER> findUser(MemberDTO memberDTO){
        Optional<MEMBER> user = userRepository.findByUserId(memberDTO.getUserId());
        return user;
    }
}