package com.example.demo.club.repository.impl;

import com.example.demo.club.domain.*;
import com.example.demo.club.repository.custom.ChatRoomRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QChatRoom qChatRoom = QChatRoom.chatRoom;
    QChat qChat = QChat.chat;
    QClub qClub = QClub.club;
    QClubInfo qClubInfo = QClubInfo.clubInfo;

    @Override
    public ChatRoom selectChatRoomByClubSeq(Long clubSeq){
        ChatRoom chatRoom = queryFactory.selectFrom(qChatRoom)
                .leftJoin(qChatRoom.club,qClub).fetchJoin()
                .where(qClub.clubSeq.eq(clubSeq))
                .fetchOne();
        return chatRoom;
    }

    public List<Chat> selectChatListByChatRoomSeq(Long chatRoomSeq){
        List<Chat> chatList = queryFactory.selectFrom(qChat)
                .where(qChat.clubInfo.clubInfoSeq.eq(chatRoomSeq))
                .orderBy(qChat.sendDt.asc())
                .fetch();
        return chatList;

    }
}
