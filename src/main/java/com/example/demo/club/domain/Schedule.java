package com.example.demo.club.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    @Column(name ="title")
    private String title;
    @Column(name ="schedule_date")
    private String scheduleDate;
    @Column(name ="start_time")
    private LocalTime startTime;
    @Column(name ="end_time")
    private LocalTime endTime;
    @Column(name ="place")
    private String place;
    @Column(name ="content")
    private String content;
}
