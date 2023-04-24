package com.example.demo.club.service;

import com.example.demo.club.common.ModelMapperUtil;
import com.example.demo.club.domain.Schedule;
import com.example.demo.club.dto.ScheduleDTO;
import com.example.demo.club.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    public List<ScheduleDTO> findClubScheduleList(Long clubSeq, LocalDateTime stDt, LocalDateTime edDt){
        List<Schedule> list = scheduleRepository.selectScheduleFromClub(clubSeq,stDt,edDt);
        List<ScheduleDTO> findClubScheduleList = ModelMapperUtil.mapAll(list, ScheduleDTO.class);
        return findClubScheduleList;
    }
}
