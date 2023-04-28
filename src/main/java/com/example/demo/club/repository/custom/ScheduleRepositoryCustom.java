package com.example.demo.club.repository.custom;

import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepositoryCustom{
    Schedule selectScheduleFromSeq(Long scheduleSeq);
    List<Schedule> selectScheduleFromClub(Long clubSeq, String stDt, String edDt);
}
