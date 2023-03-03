package com.example.demo.club.repository.custom;

import com.example.demo.club.domain.Member;

public interface MemberRepositoryCustom {
	Member fingByMemberId(String memberId);
}
