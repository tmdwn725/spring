package com.example.demo.club.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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

    @CreatedDate
    @Column(name = "send_dt")
    private LocalDateTime sendDt;
}
