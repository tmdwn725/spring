package com.example.demo.club.service;

import java.util.List;

import com.example.demo.club.domain.ClubInfo;
import com.example.demo.club.dto.ClubInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.club.common.ModelMapperUtil;
import com.example.demo.club.domain.Club;
import com.example.demo.club.dto.ClubDTO;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.repository.ClubInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubInfoService {
	
	@Autowired
	private ClubInfoRepository clubInfoRepository;
	
	public List<ClubDTO> selectMyClubList(MemberDTO dto){
		List<Club> findMyClubList = clubInfoRepository.selectMyClubList(dto.getMemberSeq());
		List<ClubDTO> selectMyClubList = ModelMapperUtil.mapAll(findMyClubList, ClubDTO.class);
		return selectMyClubList;
	}
}
