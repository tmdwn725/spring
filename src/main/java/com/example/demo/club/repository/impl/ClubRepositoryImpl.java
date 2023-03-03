package com.example.demo.club.repository.impl;

import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.QClub;
import com.example.demo.club.repository.custom.ClubRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClubRepositoryImpl implements ClubRepositoryCustom {
	
	private final JPAQueryFactory queryFactory;

	@Override
	public Club findByClub(Long clubSeq) {
	return queryFactory.selectFrom(QClub.club)
			.where(QClub.club.clubSeq.eq(clubSeq))
			.fetchOne();
	}

}
