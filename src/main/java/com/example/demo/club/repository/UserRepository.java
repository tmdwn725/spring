package com.example.demo.club.repository;

import com.example.demo.club.domain.MEMBER;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MEMBER, Long> {
    Optional<MEMBER> findByMemberId(String memberId);
}