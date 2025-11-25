package com.ra.base_spring_boot.services.impl;
import com.ra.base_spring_boot.converter.ScheduleConverter;
import com.ra.base_spring_boot.dto.req.ScheduleRequest;
import com.ra.base_spring_boot.dto.resp.ScheduleResponse;
import com.ra.base_spring_boot.dto.resp.SearchScheduleResponse;
import com.ra.base_spring_boot.model.Schedule;
import com.ra.base_spring_boot.repository.IScheduleRepository;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.Bus;
import com.ra.base_spring_boot.model.Route;
import com.ra.base_spring_boot.repository.*;
import com.ra.base_spring_boot.services.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final ScheduleConverter scheduleConverter;
    private final IRouteRepository routeRepository;
    private final IBusRepository busRepository;

    @Override
    public List<SearchScheduleResponse> searchSchedule(Long fromId, Long toId, LocalDate date) {
        return scheduleRepository.searchSchedule(fromId, toId, date);
    }

    @Override
    public Page<ScheduleResponse> getScheduleAndSortAndSearch(String search, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Schedule> pages = (search == null || search.isBlank())
                ? scheduleRepository.findAll(pageable)
                : scheduleRepository.searchSchedule(search, pageable);

        return pages.map(scheduleConverter::toScheduleResponse);

    }

    @Override
    public Schedule createSchedule(ScheduleRequest scheduleRequest) {
        Route route = routeRepository.findById(scheduleRequest.getRouteId()).orElseThrow(() -> new HttpNotFound("Route Not Found"));
        Bus bus =busRepository.findById(scheduleRequest.getBusId()).orElseThrow(() -> new HttpNotFound("Bus Not Found "));
        return  scheduleRepository.save(scheduleConverter.toSchedule(scheduleRequest, route, bus));
    }

    @Override
    public Schedule findScheduleById(int id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new HttpNotFound("Schedule Not Found"));
    }

    @Override
    public Schedule updateSchedule(int id, ScheduleRequest scheduleRequest) {
            Schedule schedule = findScheduleById(id);
            Route route = routeRepository.findById(scheduleRequest.getRouteId()).orElseThrow(() -> new HttpNotFound("Route Not Found"));
            Bus bus =busRepository.findById(scheduleRequest.getBusId()).orElseThrow(() -> new HttpNotFound("Bus Not Found "));
            schedule.setRoute(route);
            schedule.setBus(bus);
            schedule.setDepartureTime(scheduleRequest.getDeparture());
            schedule.setArrivalTime(scheduleRequest.getArrival());
            schedule.setAvailableSeat(scheduleRequest.getAvailableSeat());
            schedule.setTotalSeats(scheduleRequest.getTotalSeats());
            schedule.setStatus(scheduleRequest.getStatus());
            return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(int id) {
            Schedule schedule = findScheduleById(id);
             scheduleRepository.deleteById(id);
    }
}
