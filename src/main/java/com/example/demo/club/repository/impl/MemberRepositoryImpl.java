package com.example.demo.club.repository.impl;

import com.example.demo.club.domain.Member;
import com.example.demo.club.domain.QMember;
import com.example.demo.club.repository.custom.MemberRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
	
	private final JPAQueryFactory queryFactory;

	@Override
	public Member fingByMemberId(String memberId) {
	return queryFactory.selectFrom(QMember.member)
			.where(QMember.member.memberId.eq(memberId))
			.fetchOne();
	}



}
