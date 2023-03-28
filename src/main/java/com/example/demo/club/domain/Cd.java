package com.example.demo.club.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Getter
@Table(name = "code")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cd  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name ="cd")
	private String cd;

	@Column(name ="grp_cd")
	private String grpCd;

	@Column(name = "cd_nm")
	private String cdNm;

	public void createCd(String cd, String grpCd, String cdNm){
		this.cd = cd;
		this.grpCd = grpCd;
		this.cdNm = cdNm;
	}

}