package com.example.demo.club.repository;

import com.example.demo.club.domain.member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<member, Long> {
    Optional<member> findByMemberId(String memberId);
}