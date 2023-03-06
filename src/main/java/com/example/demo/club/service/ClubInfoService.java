package com.example.demo.club.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.Member;
import com.example.demo.club.dto.ClubDTO;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.repository.ClubInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubInfoService {
	
	@Autowired
	private ClubInfoRepository clubInfoRepository;
	
	private final ModelMapper modelMapper;

	public List<ClubDTO> selectMyClubList(MemberDTO dto){
		List<Club> findMyClubList = clubInfoRepository.selectMyClubList(dto.getMemberSeq());
		List<ClubDTO> selectMyClubList = findMyClubList.stream().map(ClubDTO::new).collect(Collectors.toList());

		return selectMyClubList;
	}
}
