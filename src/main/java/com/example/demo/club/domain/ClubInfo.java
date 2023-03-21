package com.example.demo.club.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@Table(name = "club_info")
@NoArgsConstructor
public class ClubInfo {
	@Id
    @Column(name ="club_info_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubInfoSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_seq")
	private Club club;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_seq")
	private Member member;
	
	@Column(name ="position")
	private String position;
	
	@CreatedDate
    @Column(name = "join_date")
	private LocalDateTime joinDate;
	
	@CreatedDate
    @Column(name = "withdraw_date")
	private LocalDateTime withdrawDate;

	@OneToMany(mappedBy = "clubInfo")
	List<Chat> chatList = new ArrayList<>();
	
	@Builder
	public void createClubInfo(Club club, Member member,LocalDateTime joinDate) {
		this.club = club;
		this.member = member;
		this.joinDate = joinDate;
	}

}
