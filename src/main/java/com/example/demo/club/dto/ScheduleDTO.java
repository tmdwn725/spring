package com.example.demo.club.dto;

import com.example.demo.club.domain.Club;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class ScheduleDTO {
    private long scheduleSeq;
    private String scheduleNm;
    private LocalDateTime stDt;
    private LocalDateTime edDt;
    private String place;
    private String content;
}
