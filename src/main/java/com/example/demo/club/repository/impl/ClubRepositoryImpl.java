package com.example.demo.club.repository.impl;

import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.QClub;
import com.example.demo.club.domain.QFile;
import com.example.demo.club.repository.custom.ClubRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ClubRepositoryImpl implements ClubRepositoryCustom {
	
	private final JPAQueryFactory queryFactory;

	@Override
	public Club findByClub(Long clubSeq) {
		return queryFactory.selectFrom(QClub.club)
				.where(QClub.club.clubSeq.eq(clubSeq))
				.fetchOne();
	}
	@Override
	public List<Club> findNewClub(){
		return queryFactory.select(QClub.club)
				.from(QClub.club)
				.leftJoin(QClub.club.file, QFile.file).fetchJoin()
				.fetch();
	}

}
