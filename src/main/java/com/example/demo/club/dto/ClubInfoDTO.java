package com.example.demo.club.dto;

import java.time.LocalDateTime;

import com.example.demo.club.dto.MemberDTO;

import lombok.Data;

@Data
public class ClubInfoDTO {
	private Long clubInfoSeq;
	private MemberDTO member;
	private String position;
	private LocalDateTime joinDate;
	private LocalDateTime withdrawDate;
}
