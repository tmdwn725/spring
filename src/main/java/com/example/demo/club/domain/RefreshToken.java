package com.example.demo.club.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "refresh_token")
@NoArgsConstructor
public class RefreshToken {
    @Id
    @Column(name ="refresh_token_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenSeq;
    @Column(name = "member_id")
    private String memberId;
    @Column(nullable = false)
    private String token;
    @Column(name = "expire_time")
    private LocalDateTime  expireTime;
    @Column(name = "use_yn")
    private String useYn;
}
