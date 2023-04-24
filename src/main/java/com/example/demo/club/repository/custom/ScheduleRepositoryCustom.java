package com.example.demo.club.repository.custom;

import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepositoryCustom{
    List<Schedule> selectScheduleFromClub(Long clubSeq, LocalDateTime stDt, LocalDateTime edDt);
}
