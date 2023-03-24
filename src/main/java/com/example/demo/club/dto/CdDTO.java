package com.example.demo.club.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CdDTO {
    private Long id;
    private String cd;
    private String grpCd;
    private String grpCdNm;
    private String cdNm;
}
