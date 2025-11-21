package com.ra.base_spring_boot.services;
import com.ra.base_spring_boot.dto.resp.SearchScheduleResponse;
import java.time.LocalDate;
import java.util.List;

public interface IScheduleService {

    List<SearchScheduleResponse> searchSchedule(Long fromId, Long toId, LocalDate date);

}
