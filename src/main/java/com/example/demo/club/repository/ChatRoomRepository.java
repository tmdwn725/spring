package com.example.demo.club.repository;

import com.example.demo.club.domain.ChatRoom;
import com.example.demo.club.repository.custom.ChatRoomRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {
}
