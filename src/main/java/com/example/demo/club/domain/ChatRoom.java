package com.example.demo.club.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @Column(name ="chat_room_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomSeq;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_seq")
    private Club club;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    @Builder
    public void createChatRoom(Club club, Member member){
        this.club = club;
        this.member = member;
    }
}
