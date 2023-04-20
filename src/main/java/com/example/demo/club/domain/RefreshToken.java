package com.example.demo.club.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.time.LocalDateTime;

//@Entity
@Getter
@Setter
@RedisHash(value = "jwtToken", timeToLive = 60*60*24*3)
@NoArgsConstructor
public class RefreshToken {
    private String memberId;
    private String token;
    private LocalDateTime  expireTime;
    private String useYn;
}
