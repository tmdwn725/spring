package com.example.demo.club.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ScheduleDTO {
    private long scheduleSeq;
    private long clubSeq;
    private String title;
    private String scheduleDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String stTime;
    private String edTime;
    private String stDt;
    private String edDt;
    private String place;
    private String content;
    private String date;
}
