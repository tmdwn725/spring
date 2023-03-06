package com.example.demo.club.dto;

import com.example.demo.club.domain.Club;
import lombok.Builder;
import lombok.Data;

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

	public ClubDTO(Club club) {
		schoolCd = club.getSchoolCd();
		clubNm = club.getClubNm();
		roomNm = club.getRoomNm();
		regDt = club.getRegDt();
		modDt = club.getModDt();
	}
}
