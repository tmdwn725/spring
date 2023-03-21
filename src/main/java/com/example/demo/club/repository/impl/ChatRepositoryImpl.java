package com.example.demo.club.repository.impl;

import com.example.demo.club.domain.Chat;
import com.example.demo.club.domain.QChat;
import com.example.demo.club.domain.QClub;
import com.example.demo.club.domain.QClubInfo;
import com.example.demo.club.repository.custom.ChatRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QChat qChat = QChat.chat;
    QClub qClub = QClub.club;
    QClubInfo qClubInfo = QClubInfo.clubInfo;

    public List<Chat> selectChatListByClubSeq(Long clubSeq){
        List<Chat> selectChatList = queryFactory.select(qChat)
                                                .from(qClub)
                                                .leftJoin(qClub.clubInfoList, qClubInfo)
                                                .leftJoin(qClubInfo.chatList, qChat)
                                                .where(qClub.clubSeq.eq(clubSeq))
                                                .orderBy(qChat.sendDt.asc())
                                                .fetch();
        return selectChatList;
    }

}
