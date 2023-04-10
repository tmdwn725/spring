package com.example.demo.club.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RedisHash(value = "refreshToken", timeToLive = 60)
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
