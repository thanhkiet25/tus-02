package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.ScheduleResponse;

import java.time.LocalDate;
import java.util.List;

public interface IScheduleService {
    List<ScheduleResponse> searchSchedule(Long fromId, Long toId, LocalDate date);
}
