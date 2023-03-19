package com.example.demo.club.repository.impl;

import com.example.demo.club.domain.Chat;
import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.QChat;
import com.example.demo.club.domain.QClubInfo;
import com.example.demo.club.repository.custom.ChatRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QChat qchat = QChat.chat;

    @Override
    public void insertChat(Chat chat){
        queryFactory.insert(qchat)
                .execute();
    }

}
