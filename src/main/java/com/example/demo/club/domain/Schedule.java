package com.example.demo.club.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "schedule")
public class Schedule extends BaseEntity{
    @Id
    @Column(name ="schedule_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleSeq;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_seq")
    private Club club;
    @Column(name ="schedule_nm")
    private String scheduleNm;
    @Column(name ="st_dt")
    private LocalDateTime stDt;
    @Column(name ="ed_dt")
    private LocalDateTime edDt;
    @Column(name ="place")
    private String place;
    @Column(name ="content")
    private String content;
}
