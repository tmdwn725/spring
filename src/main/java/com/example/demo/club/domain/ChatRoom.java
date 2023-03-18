package com.example.demo.club.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @Column(name ="chat_room_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomSeq;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Club club;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Column(name = "send_dt")
    private LocalDateTime sendDt;

    @OneToMany(mappedBy = "chatRoom")
    private List<Chat>  list = new ArrayList<>();
}
