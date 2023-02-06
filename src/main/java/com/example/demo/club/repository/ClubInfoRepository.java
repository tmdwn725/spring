package com.example.demo.club.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.ClubInfo;


public interface ClubInfoRepository extends JpaRepository<ClubInfo, Long> {
}
	
	
	