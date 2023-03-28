package com.example.demo.club.repository;

import com.example.demo.club.domain.Member;
import com.example.demo.club.repository.custom.MemberRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>,MemberRepositoryCustom {
    Optional<Member> findByMemberId(String memberId);

}