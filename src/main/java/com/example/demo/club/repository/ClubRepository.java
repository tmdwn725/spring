package com.example.demo.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.club.domain.Club;

public interface ClubRepository extends JpaRepository<Club, Long>  {
	

}
