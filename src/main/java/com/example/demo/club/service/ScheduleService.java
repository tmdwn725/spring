package com.example.demo.club.service;

import com.example.demo.club.common.ModelMapperUtil;
import com.example.demo.club.domain.Club;
import com.example.demo.club.domain.ClubInfo;
import com.example.demo.club.domain.Member;
import com.example.demo.club.domain.Schedule;
import com.example.demo.club.dto.ClubDTO;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.dto.ScheduleDTO;
import com.example.demo.club.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleDTO findClubSchedule(ScheduleDTO scheduleDTO){
        ScheduleDTO schedule  = ModelMapperUtil.map(scheduleRepository.selectScheduleFromSeq(scheduleDTO.getScheduleSeq()),ScheduleDTO.class);
        return schedule;
    }

    public List<ScheduleDTO> findClubScheduleList(ScheduleDTO schedule){
        List<Schedule> list = scheduleRepository.selectScheduleFromClub(schedule.getClubSeq(), schedule.getStDt(),schedule.getEdDt());
        List<ScheduleDTO> findClubScheduleList = ModelMapperUtil.mapAll(list, ScheduleDTO.class);
        return findClubScheduleList;
    }

    @Transactional
    public void createSchedule(ScheduleDTO scheduleDTO, ClubDTO clubDTO) {
        // 일정 생성
        Schedule schedule = new Schedule();
        Club club = new Club();
        club.setClubSeq(scheduleDTO.getClubSeq());

        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setPlace(scheduleDTO.getPlace());
        schedule.setContent(scheduleDTO.getContent());
        schedule.setScheduleDate(scheduleDTO.getScheduleDate());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        schedule.setStartTime(LocalTime.parse(scheduleDTO.getStTime(), formatter));
        schedule.setEndTime(LocalTime.parse(scheduleDTO.getEdTime(), formatter));

        schedule.setClub(club);

        scheduleRepository.save(schedule);
    }

    @Transactional
    public void modifySchedule(ScheduleDTO scheduleDTO) {
        // 일정 생성
        Schedule schedule = new Schedule();
        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setPlace(scheduleDTO.getPlace());
        schedule.setContent(scheduleDTO.getContent());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        schedule.setStartTime(LocalTime.parse(scheduleDTO.getStTime(), formatter));
        schedule.setEndTime(LocalTime.parse(scheduleDTO.getEdTime(), formatter));
        schedule.setScheduleSeq(scheduleDTO.getScheduleSeq());
        scheduleRepository.updateSchedule(schedule);
    }

    @Transactional
    public void removeSchedule(ScheduleDTO scheduleDTO) {
        // 일정 삭제
        Schedule schedule = new Schedule();
        schedule.setScheduleSeq(scheduleDTO.getScheduleSeq());
        scheduleRepository.delete(schedule);
    }
}
