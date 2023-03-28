package com.example.demo.club.repository.impl;

import com.example.demo.club.domain.Cd;

import com.example.demo.club.domain.QCd;
import com.example.demo.club.repository.custom.CdRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CdRepositoryImpl implements CdRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QCd qCd = QCd.cd1;
    public List<Cd> selectCdList(String grpCd){
        List<Cd> selectCdList = queryFactory.selectFrom(qCd)
                .where(qCd.grpCd.eq(grpCd))
                .fetch();
        return selectCdList;
    }
}
