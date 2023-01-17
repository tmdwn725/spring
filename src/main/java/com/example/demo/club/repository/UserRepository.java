package com.example.demo.club.repository;

import com.example.demo.club.domain.USER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<USER, Long>{

}
