package com.ra.base_spring_boot.services;
import com.ra.base_spring_boot.dto.resp.SearchScheduleResponse;
import com.ra.base_spring_boot.model.Schedule;
import com.ra.base_spring_boot.dto.req.ScheduleRequest;
import com.ra.base_spring_boot.dto.resp.ScheduleResponse;
import org.springframework.data.domain.Page;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IScheduleService {

    List<SearchScheduleResponse> searchSchedule(Long fromId, Long toId, LocalDate date);

    Page<ScheduleResponse> getScheduleAndSortAndSearch(String search, int page, int size, String sortBy, String direction);

    Schedule createSchedule(ScheduleRequest scheduleRequest);

    Schedule findScheduleById(int id);

    Schedule updateSchedule(int id, ScheduleRequest scheduleRequest);

    void deleteSchedule(int id);

}
