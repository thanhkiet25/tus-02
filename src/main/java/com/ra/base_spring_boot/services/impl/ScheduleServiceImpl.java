package com.ra.base_spring_boot.services.impl;
import com.ra.base_spring_boot.dto.resp.SearchScheduleResponse;
import com.ra.base_spring_boot.repository.IScheduleRepository;
import com.ra.base_spring_boot.services.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements IScheduleService {
    private final IScheduleRepository scheduleRepository;

    @Override
    public List<SearchScheduleResponse> searchSchedule(Long fromId, Long toId, LocalDate date) {
        return scheduleRepository.searchSchedule(fromId, toId, date);
    }
}
