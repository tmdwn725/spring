package com.example.demo.club.repository.custom;

import com.example.demo.club.domain.Chat;

import java.util.List;

public interface ChatRepositoryCustom {
    List<Chat> selectChatListByClubSeq(Long clubSeq);
}
