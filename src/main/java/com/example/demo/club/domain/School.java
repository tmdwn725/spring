package com.example.demo.club.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "school")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class School extends BaseEntity {

	@Id
	@Column(name ="school_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long schoolSeq;

	@Column(name ="school_cd")
	private String schoolCd;

	@Column(name = "school_nm")
	private String schoolNm;

	@Column(name = "address")
	private String address;

	@Column(name = "tel_no")
	private String telNo;



}
