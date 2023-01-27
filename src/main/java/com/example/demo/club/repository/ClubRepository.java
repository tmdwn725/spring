package com.example.demo.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.club.domain.club;

public interface ClubRepository extends JpaRepository<club, Long>  {
	

}
