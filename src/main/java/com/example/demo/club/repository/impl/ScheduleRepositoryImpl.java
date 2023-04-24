package com.example.demo.club.repository.impl;

import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.QSchedule;
import com.example.demo.club.domain.Schedule;
import com.example.demo.club.repository.custom.ScheduleRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QSchedule schedule = QSchedule.schedule;
    public List<Schedule> selectScheduleFromClub(Long clubSeq, LocalDateTime stDt, LocalDateTime edDt){
        return queryFactory.selectFrom(schedule)
                .where(schedule.club.clubSeq.eq(clubSeq).and(schedule.edDt.goe(stDt).and(schedule.stDt.loe(edDt))))
                .fetch();
    }
}
