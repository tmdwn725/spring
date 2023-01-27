package com.example.demo.club.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.club.domain.club;
import com.example.demo.club.repository.ClubRepository;

@Service
public class ClubService {
	@Autowired
    private ClubRepository clubRepository;
	
	public List<club> selectClubList(){
		return clubRepository.findAll();
	}
}
