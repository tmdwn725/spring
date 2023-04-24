package com.example.demo.club.dto;


import com.example.demo.club.domain.Role;
import lombok.Data;

@Data
public class MemberDTO {
	private long memberSeq;
	private String memberId;
	private String memberNm;
	private String password;
	private String authority;
	private Role role;
}
