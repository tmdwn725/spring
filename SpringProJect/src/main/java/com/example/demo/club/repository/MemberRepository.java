package com.example.demo.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.club.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

}
