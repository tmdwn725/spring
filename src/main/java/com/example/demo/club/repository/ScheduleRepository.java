package com.example.demo.club.repository;

import com.example.demo.club.domain.Schedule;
import com.example.demo.club.repository.custom.ScheduleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {
}
