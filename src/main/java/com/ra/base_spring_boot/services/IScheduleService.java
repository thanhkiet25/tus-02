package com.ra.base_spring_boot.services;
import com.ra.base_spring_boot.dto.req.ScheduleUpdateRequest;
import com.ra.base_spring_boot.dto.resp.SearchScheduleResponse;
import com.ra.base_spring_boot.dto.req.ScheduleCreateRequest;
import com.ra.base_spring_boot.dto.resp.ScheduleResponse;
import com.ra.base_spring_boot.dto.resp.SeatAvailabilityResponse;
import org.springframework.data.domain.Page;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IScheduleService {

    List<SearchScheduleResponse> searchSchedule(Long fromId, Long toId, LocalDate date);

    Page<ScheduleResponse> getScheduleAndSortAndSearch(String search, int page, int size, String sortBy, String direction);

    ScheduleResponse createSchedule(ScheduleCreateRequest scheduleRequest);

    ScheduleResponse updateSchedule(int id, ScheduleUpdateRequest scheduleUpdateRequest);

    ScheduleResponse deleteSchedule(int id);


    List<SeatAvailabilityResponse> getSeatsByScheduleId(Integer scheduleId);

}
