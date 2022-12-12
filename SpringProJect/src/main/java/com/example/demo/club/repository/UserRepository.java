package com.example.demo.club.repository;

import com.example.demo.club.domain.USER;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<USER, Long>{

}
