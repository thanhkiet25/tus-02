package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.SearchScheduleDTO;

import java.time.LocalDate;
import java.util.List;

public interface IScheduleService {
    List<SearchScheduleDTO> searchSchedule(Long fromId, Long toId, LocalDate date);
}
