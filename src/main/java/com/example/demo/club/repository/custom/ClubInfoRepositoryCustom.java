package com.example.demo.club.repository.custom;

import java.util.List;

import com.example.demo.club.domain.Club;

public interface ClubInfoRepositoryCustom {
	List<Club> selectMyClubList(Long memberSeq);
}
