package com.example.demo.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.club.domain.Club;

public interface ClubRepository extends JpaRepository<Club, Long>  {
	
	@Query("SELECT c FROM Club c WHERE c.clubSeq = :clubSeq")
	Club findByClub(@Param("clubSeq") Long clubSeq);
	

}
