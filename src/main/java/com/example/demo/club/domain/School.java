package com.example.demo.club.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "school")
@NoArgsConstructor
public class School extends BaseEntity {
	@Id
    @Column(name ="school_cd")
	private String schoolCd;
	@Column(name = "school_nm")
	private String schoolNm;
	@Column(name = "address")
	private String address;
	@Column(name = "tel_no")
	private String telNo;
	public void createSchool(String schoolCd,String schoolNm, String address,String telNo){
		this.schoolCd = schoolCd;
		this.schoolNm = schoolNm;
		this.address = address;
		this.telNo = telNo;
	}
}