package com.example.demo.club.repository.custom;

import com.example.demo.club.domain.Chat;
import com.example.demo.club.domain.ChatRoom;

import java.util.List;

public interface ChatRoomRepositoryCustom {
    ChatRoom selectChatRoomByClubSeq(Long clubSeq);

    List<Chat>selectChatListByChatRoomSeq(Long chatRoomSeq);
}
