package com.example.demo.club.dto;

import lombok.Data;

/**
 * jwt 토큰 관련 DTO
 */
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