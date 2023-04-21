package com.example.demo.club.repository.impl;

import com.example.demo.club.domain.Chat;
import com.example.demo.club.domain.QChat;
import com.example.demo.club.domain.QClub;
import com.example.demo.club.domain.QClubInfo;
import com.example.demo.club.dto.ChatDTO;
import com.example.demo.club.repository.custom.ChatRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Projection;

import java.util.List;

@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QChat qChat = QChat.chat;
    QClub qClub = QClub.club;
    QClubInfo qClubInfo = QClubInfo.clubInfo;
    public List selectChatListByClubSeq(Long clubSeq){
        List<ChatDTO> selectChatList = queryFactory.select(Projections.fields(ChatDTO.class
                        , qChat.chatSeq, qChat.message, qChat.sendDt
                        , qClubInfo.member.memberNm.as("sender"), qClubInfo.clubInfoSeq))
                                                .from(qClub)
                                                .innerJoin(qClub.clubInfoList, qClubInfo)
                                                .innerJoin(qClubInfo.chatList, qChat)
                                                .where(qClub.clubSeq.eq(clubSeq))
                                                .orderBy(qChat.sendDt.asc())
                                                .fetch();
        return selectChatList;
    }

}
