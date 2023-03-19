package com.example.demo.club.repository.custom;

import com.example.demo.club.domain.ChatRoom;

public interface ChatRoomRepositoryCustom {
    ChatRoom selectChatRoomByClubSeq(Long clubSeq);
}
