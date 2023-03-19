package com.example.demo.club.repository;

import com.example.demo.club.domain.Chat;
import com.example.demo.club.repository.custom.ChatRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,Long>, ChatRepositoryCustom {
}
