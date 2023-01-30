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
@Table(name = "cd")
@NoArgsConstructor
public class cd {
	@Id
    @Column(name ="cd")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String cd;
	private String cdNm;
	private String regDt;
	private String modDt;
}

