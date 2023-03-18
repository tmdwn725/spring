package com.example.demo.club.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @Column(name ="chat_room_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomSeq;

    @Column(name = "club_cd")
    private String clubSeq;

    @Column(name= "member_seq")
    private String memberSeq;
}
