package com.example.demo.club.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat")
public class Chat {
    @Id
    @Column(name ="chat_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatSeq;
    @Column(name = "message")
    private String message;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_seq")
    private ChatRoom chatRoom;
}
