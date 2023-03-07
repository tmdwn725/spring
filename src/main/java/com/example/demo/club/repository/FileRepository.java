package com.example.demo.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.club.domain.File;

public interface FileRepository extends JpaRepository<File, Long>{

}
