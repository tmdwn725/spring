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

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Code extends BaseEntity {

	@Id
    @Column(name ="cd")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cd;

	@Column(name = "code_nm")
	private String codeNm;

}