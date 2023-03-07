package com.example.demo.club.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "club")
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
	
	@Column(name = "introduce")
	private String introduce;

	@OneToMany(mappedBy = "club")
	private List<ClubInfo> clubInfoList = new ArrayList<>();
	
	@OneToOne
	@JoinColumn(name="rpt_img_fl_seq")
	private File file;

	@Builder
    public void createClub(String schoolCd, String clubClsCd, String clubNm, String roomNm,String introduce, File file){
        this.schoolCd = schoolCd;
        this.clubClsCd = clubClsCd;
        this.clubNm = clubNm;
        this.roomNm = roomNm;
        this.introduce = introduce;
        this.file = file;
    }

}
