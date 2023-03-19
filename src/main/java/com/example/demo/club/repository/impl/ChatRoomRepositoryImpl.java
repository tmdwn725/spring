package com.example.demo.club.repository.impl;

import com.example.demo.club.domain.ChatRoom;
import com.example.demo.club.domain.QChatRoom;
import com.example.demo.club.domain.QClub;
import com.example.demo.club.domain.QClubInfo;
import com.example.demo.club.repository.custom.ChatRoomRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QChatRoom qChatRoom = QChatRoom.chatRoom;
    QClub qClub = QClub.club;

    @Override
    public ChatRoom selectChatRoomByClubSeq(Long clubSeq){
        ChatRoom chatRoom = queryFactory.selectFrom(qChatRoom)
                .leftJoin(qChatRoom.club,qClub).fetchJoin()
                .where(qClub.clubSeq.eq(clubSeq))
                .fetchOne();
        return chatRoom;
    }
}
