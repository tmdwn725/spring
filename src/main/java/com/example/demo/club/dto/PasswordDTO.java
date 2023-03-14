package com.example.demo.club.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PasswordDTO {

    private long memberSeq;

    @NotEmpty(message = "이전 패스워드를 입력해주세요")
    private String beforePassword;

    @NotEmpty(message = "변경 패스워드를 입력해주세요")
    private String afterPassword;

    @NotEmpty(message = "패스워드 확인을 입력해주세요")
    private String confirmPassword;

}
