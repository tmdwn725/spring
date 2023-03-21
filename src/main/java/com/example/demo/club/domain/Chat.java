package com.example.demo.club.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat")
public class Chat {
    @Id
    @Column(name ="chat_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatSeq;

    @Column(name = "message")
    private String message;

    @CreatedDate
    @Column(name = "send_dt")
    private LocalDateTime sendDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_info_seq")
    private ClubInfo clubInfo;
}
