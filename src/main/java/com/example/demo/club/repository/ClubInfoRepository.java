package com.example.demo.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.club.domain.ClubInfo;
import com.example.demo.club.repository.custom.ClubInfoRepositoryCustom;


public interface ClubInfoRepository extends JpaRepository<ClubInfo, Long>,ClubInfoRepositoryCustom {
}
	
	
	