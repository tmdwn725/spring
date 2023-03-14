package com.example.demo.club.service;

import java.util.List;

import com.example.demo.club.common.ModelMapperUtil;
import com.example.demo.club.domain.File;
import com.example.demo.club.dto.FileDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.club.domain.Club;
import com.example.demo.club.dto.ClubDTO;
import com.example.demo.club.repository.ClubRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService {

    private final ClubRepository clubRepository;
    
    private final ModelMapper modelMapper;


	public List<ClubDTO> selectClubList(){
		List<ClubDTO> selectClubList = ModelMapperUtil.mapAll(clubRepository.findNewClub(), ClubDTO.class);
		return selectClubList;
	}

	public ClubDTO selectClub(Long clubSeq) {
		ClubDTO dto = modelMapper.map(clubRepository.findByClub(clubSeq), ClubDTO.class);
		return dto;
	}

	@Transactional
	public void createClub(Club club) {
		clubRepository.save(club);
	}

}
