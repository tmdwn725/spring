package com.example.demo.club.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.ClubInfo;
import com.example.demo.club.domain.Member;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.repository.ClubInfoRepository;

@Service
public class ClubInfoService {
	
	@Autowired
	private ClubInfoRepository clubInfoRepository;

	public List<ClubInfo> selectMyClubList(MemberDTO dto){
		Member member = new Member();
		member.setMemberSeq(dto.getMemberSeq());

		return clubInfoRepository.findAll();
	}
	

}
