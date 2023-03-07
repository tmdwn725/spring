package com.example.demo.club.dto;

import com.example.demo.club.domain.Club;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
public class ClubDTO {
	private Long clubSeq;
	private String schoolCd;
	private String clubClsCd;
	private String clubNm;
	private String roomNm;
	private LocalDateTime regDt;
	private LocalDateTime modDt;
}
