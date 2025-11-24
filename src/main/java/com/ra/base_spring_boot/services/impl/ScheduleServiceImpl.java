package com.ra.base_spring_boot.services.impl;
import com.ra.base_spring_boot.dto.resp.SearchScheduleResponse;
import com.ra.base_spring_boot.model.Schedule;
import com.ra.base_spring_boot.repository.IScheduleRepository;
import com.ra.base_spring_boot.services.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements IScheduleService {
    private final IScheduleRepository scheduleRepository;
    private final IScheduleRepository iScheduleRepository;

    @Override
    public List<SearchScheduleResponse> searchSchedule(Long fromId, Long toId, LocalDate date) {
        return scheduleRepository.searchSchedule(fromId, toId, date);
    }

    @Override
    public Set<Schedule> findSchedule(List<Integer> id) {
        Set<Schedule> list = new HashSet<>();
        for (Integer i : id){
            list.add(iScheduleRepository.findScheduleById(i));
        }
        return list;
    }
}
