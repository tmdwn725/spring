package com.example.demo.club.repository.impl;

import java.util.List;

import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.QChatRoom;
import com.example.demo.club.domain.QClubInfo;
import com.example.demo.club.repository.custom.ClubInfoRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClubInfoRepositoryImpl implements ClubInfoRepositoryCustom {
	
	private final JPAQueryFactory queryFactory;
	
	@Override
	public List<Club> selectMyClubList(Long memberSeq){
		List<Club> result =  (List<Club>) queryFactory.select(QClubInfo.clubInfo.club)
						.from(QClubInfo.clubInfo)
						.where(QClubInfo.clubInfo.member.memberSeq.eq(memberSeq))
						.fetch();
		return result;
		
	}
	
}
	
	
	