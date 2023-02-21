package com.example.demo.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.club.domain.School;

public interface SchoolRepository extends JpaRepository<School, String>{

}
