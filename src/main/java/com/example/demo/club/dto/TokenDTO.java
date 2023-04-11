package com.example.demo.club.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
    private long expireTime;

    public TokenDTO(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}