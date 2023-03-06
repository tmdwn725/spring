package com.example.demo.club.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.example.demo.club.domain.School.VoidBuilder;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "club_info")
@NoArgsConstructor
public class ClubInfo {
	@Id
    @Column(name ="club_info_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubInfoSeq;

	@ManyToOne
	@JoinColumn(name = "club_seq")
	private Club club;
	
	@ManyToOne
	@JoinColumn(name = "member_seq")
	private Member member;
	
	@Column(name ="position")
	private String position;
	
	@CreatedDate
    @Column(name = "join_date")
	private LocalDate joinDate;
	
	@CreatedDate
    @Column(name = "withdraw_date")
	private LocalDate withdrawDate;
	
	@Builder
	public void createClubInfo(Club club, Member member,LocalDate joinDate) {
		this.club = club;
		this.member = member;
		this.joinDate = joinDate;
	}

}
