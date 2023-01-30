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
@Table(name = "code")
@NoArgsConstructor
public class Code {
	@Id
    @Column(name ="cd")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String cd;

	@Column(name = "code_nm")
	private String codeNm;

	@Column(name = "reg_dt")
	private String regDt;

	@Column(name = "mod_dt")
	private String modDt;
}

