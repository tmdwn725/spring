package com.example.demo.club.repository.custom;

import com.example.demo.club.domain.Club;

import java.util.List;

public interface ClubRepositoryCustom {
	Club findByClub(Long clubSeq);
	List<Club> findNewClub();
}
