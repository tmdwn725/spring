package com.example.demo.club.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
}