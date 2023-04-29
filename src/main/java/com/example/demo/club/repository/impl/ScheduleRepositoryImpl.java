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
    public Schedule selectScheduleFromSeq(Long scheduleSeq){
        return queryFactory.selectFrom(schedule)
                .where(schedule.scheduleSeq.eq(scheduleSeq))
                .fetchOne();
    }
    public List<Schedule> selectScheduleFromClub(Long clubSeq, String stDt, String edDt){
        return queryFactory.selectFrom(schedule)
                .where(schedule.club.clubSeq.eq(clubSeq)
                .and(schedule.scheduleDate.between(stDt,edDt)))
                .orderBy(schedule.startTime.asc())
                .fetch();
    }
    public void updateSchedule(Schedule shdl){
        queryFactory.update(schedule)
                .set(schedule.title, shdl.getTitle())
                .set(schedule.place, shdl.getPlace())
                .set(schedule.content, shdl.getContent())
                .set(schedule.startTime, shdl.getStartTime())
                .set(schedule.endTime, shdl.getEndTime())
                .where(schedule.scheduleSeq.eq(shdl.getScheduleSeq()));
    }
}
