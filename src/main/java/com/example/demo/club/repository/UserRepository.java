package com.example.demo.club.repository;

import com.example.demo.club.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId);
}