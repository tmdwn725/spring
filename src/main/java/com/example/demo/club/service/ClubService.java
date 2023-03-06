package com.example.demo.club.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.club.domain.Club;
import com.example.demo.club.dto.ClubDTO;
import com.example.demo.club.repository.ClubRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService {

    private final ClubRepository clubRepository;
    
    private final ModelMapper modelMapper;

	public List<Club> selectClubList(){
		return clubRepository.findAll();
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
