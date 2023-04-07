package com.example.demo.club.repository.impl;

import com.example.demo.club.domain.QRefreshToken;
import com.example.demo.club.domain.RefreshToken;
import com.example.demo.club.repository.custom.RefreshTokenRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QRefreshToken refreshToken = QRefreshToken.refreshToken;
    public RefreshToken findTokenByMemberIdId(String memberId){
        return queryFactory.selectFrom(refreshToken)
                .where(memberIdEq(memberId),useYnEq("Y"))
                .fetchOne();
    }
    private BooleanExpression memberIdEq(String memberId){
        return Objects.nonNull(memberId) ? refreshToken.memberId.eq(memberId) : null;
    }
    private BooleanExpression useYnEq(String useYn){
        return Objects.nonNull(useYn) ? refreshToken.useYn.eq(useYn) : null;
    }
}
