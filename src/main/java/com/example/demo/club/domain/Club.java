package com.example.demo.club.domain;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "club")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseEntity {

	@Id
    @Column(name ="club_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubSeq;

	@Column(name = "school_cd")
	private String schoolCd;

	@Column(name = "club_cls_cd")
	private String clubClsCd;

	@Column(name = "club_nm")
	private String clubNm;

	@Column(name = "room_nm")
	private String roomNm;

	@OneToMany(mappedBy = "club")
	private List<ClubInfo> clubInfoList = new ArrayList<>();

	public void create(Club club) {
		this.schoolCd = club.getSchoolCd();
		this.clubNm = club.getClubNm();
		this.clubClsCd = club.getClubClsCd();
		this.roomNm = club.getRoomNm();
	}

}
