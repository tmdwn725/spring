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

	QClub club = QClub.club;
	QFile file = QFile.file;

	@Override
	public Club findByClub(Long clubSeq) {
		return queryFactory.selectFrom(club)
				.where(club.clubSeq.eq(clubSeq))
				.fetchOne();
	}
	@Override
	public List<Club> findNewClub(){
		return queryFactory.selectFrom(club)
				.leftJoin(club.file,file).fetchJoin()
				.fetch();
	}

}
