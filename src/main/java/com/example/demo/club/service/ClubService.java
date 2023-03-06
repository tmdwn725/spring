package com.example.demo.club.service;

import java.util.List;
import java.util.stream.Collectors;

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

	public List<ClubDTO> selectClubList(){
		
		List<Club> findAllClubList = clubRepository.findAll();		
		
		List<ClubDTO> selectAllClubList = findAllClubList.stream().map(p -> modelMapper.map(p, ClubDTO.class)).collect(Collectors.toList());
		return selectAllClubList;
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
