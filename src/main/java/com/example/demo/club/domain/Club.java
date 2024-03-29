package com.example.demo.club.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
	@Transient
	private String filePth;

	@OneToMany(mappedBy = "club", cascade = CascadeType.PERSIST)
	private List<ClubInfo> clubInfoList = new ArrayList<>();

	@OneToMany(mappedBy = "club", cascade = CascadeType.PERSIST)
	private List<Schedule> scheduleList = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="rpt_img_fl_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private File file;

	@Builder
    public void createClub(String schoolCd, String clubClsCd, String clubNm, String roomNm,String introduce, File file){
        this.schoolCd = schoolCd;
        this.clubClsCd = clubClsCd;
        this.clubNm = clubNm;
        this.roomNm = roomNm;
        this.introduce = introduce;
		this.file =  file;
    }

}
