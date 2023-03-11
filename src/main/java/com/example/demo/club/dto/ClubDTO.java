package com.example.demo.club.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.club.domain.ClubInfoDTO;

import lombok.Data;

@Data
public class ClubDTO {
	private Long clubSeq;
	private String schoolCd;
	private String clubClsCd;
	private String clubNm;
	private String roomNm;
	private LocalDateTime regDt;
	private LocalDateTime modDt;
	private String introduce;
	private String filePth;
	private List<ClubInfoDTO> clubInfoList;
}
