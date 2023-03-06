package com.example.demo.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.club.domain.Club;
import com.example.demo.club.repository.custom.ClubRepositoryCustom;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryCustom  {
    public Club findByClubNm(String clubNm);
}
