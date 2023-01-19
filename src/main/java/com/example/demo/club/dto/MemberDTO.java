package com.example.demo.club.dto;


import com.example.demo.club.domain.Role;
import lombok.Data;

@Data
public class MemberDTO {

	private long id;
	private String userId;
	private String userPassword;
	private Role role;

}
