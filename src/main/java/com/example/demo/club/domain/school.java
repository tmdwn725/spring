package com.example.demo.club.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "school")
@NoArgsConstructor
public class school {
	@Id
    @Column(name ="school_cd")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String schoolCd;
	private String schoolNm;
	private String address;
	private String telNo;
	private String regDt;
	private String modDt;	
}
