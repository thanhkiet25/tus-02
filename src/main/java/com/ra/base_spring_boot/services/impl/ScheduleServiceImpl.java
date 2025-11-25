package com.ra.base_spring_boot.services.impl;
import com.ra.base_spring_boot.dto.req.ScheduleCreateRequest;
import com.ra.base_spring_boot.dto.req.ScheduleUpdateRequest;
import com.ra.base_spring_boot.dto.resp.ScheduleResponse;
import com.ra.base_spring_boot.dto.resp.SearchScheduleResponse;
import com.ra.base_spring_boot.model.Schedule;
import com.ra.base_spring_boot.repository.IScheduleRepository;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.Bus;
import com.ra.base_spring_boot.model.Route;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.model.constants.ScheduleStatus;
import com.ra.base_spring_boot.repository.*;
import com.ra.base_spring_boot.services.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements IScheduleService {
    private final IScheduleRepository scheduleRepository;
    private final IScheduleRepository iScheduleRepository;
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

        return pages.map(ScheduleResponse::new);

    }

    @Override
    public ScheduleResponse createSchedule(ScheduleCreateRequest scheduleRequest) {
       Bus bus = busRepository.findById(scheduleRequest.getBusId()).orElseThrow(() -> new HttpNotFound("Bus is not found"));
       Route route = routeRepository.findById(scheduleRequest.getRouteId()).orElseThrow(() -> new HttpNotFound("Route is not found"));

        String departureStation = route.getDepartureStation().getName();
        String arrivalStation = route.getArrivalStation().getName();

        LocalDateTime departureTime = scheduleRequest.getDepartureTime();
        LocalDateTime arrivalTime = scheduleRequest.getArrivalTime();

       int conflict = scheduleRepository.overLappingSchedules(
               bus.getId(),
               scheduleRequest.getDepartureTime(),
               scheduleRequest.getArrivalTime());
       if (conflict>0) {
           throw new HttpConflict("this bus is already has a schedule that overlaps this time");
       }
       Schedule schedule = new Schedule();
       schedule.setBus(bus);
       schedule.setRoute(route);
       schedule.setDepartureTime(scheduleRequest.getDepartureTime());
       schedule.setArrivalTime(scheduleRequest.getArrivalTime());
       schedule.setAvailableSeat(scheduleRequest.getAvailableSeat());
       schedule.setTotalSeats(scheduleRequest.getTotalSeats());
       schedule.setStatus(scheduleRequest.getStatus()!= null ? scheduleRequest.getStatus() : ScheduleStatus.AVAILABLE);

        Schedule saved = scheduleRepository.save(schedule);

       return new ScheduleResponse(saved);
    }

    @Override
    public ScheduleResponse updateSchedule(int id, ScheduleUpdateRequest  updateRequest) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()-> new HttpNotFound("Schedule not found"));
        if(updateRequest.getBusId()!= null){
            Bus bus = busRepository.findById(updateRequest.getBusId()).orElseThrow(() -> new HttpNotFound("Bus is not found"));
            schedule.setBus(bus);
       }

        if(updateRequest.getRouteId()!= null){
            Route route = routeRepository.findById(updateRequest.getRouteId()).orElseThrow(() -> new HttpNotFound("Route is not found"));
            schedule.setRoute(route);
        }

        LocalDateTime departureTime = updateRequest.getDepartureTime() != null ? updateRequest.getDepartureTime() : schedule.getDepartureTime();
        LocalDateTime arrivalTime =updateRequest.getArrivalTime() != null ? updateRequest.getArrivalTime() : schedule.getArrivalTime();
        if(!departureTime.isBefore(arrivalTime)){
                throw new HttpBadRequest("Departure time cannot be before Arrival time");
        }
        if(!departureTime.equals(schedule.getDepartureTime())|| !arrivalTime.equals( schedule.getArrivalTime())){
            int Conflict = scheduleRepository.overLappingSchedules(
                            schedule.getBus().getId(),
                            schedule.getId(),
                            updateRequest.getDepartureTime(),
                            updateRequest.getArrivalTime()
            );
            if (Conflict>0) {
                throw new HttpConflict("this bus is already has a schedule that overlaps this time");
            }
            schedule.setDepartureTime(departureTime);
            schedule.setArrivalTime(arrivalTime);
        }
        schedule.setAvailableSeat(updateRequest.getAvailableSeat()!=null?updateRequest.getAvailableSeat():schedule.getAvailableSeat());
        schedule.setTotalSeats(updateRequest.getTotalSeats()!=null?updateRequest.getTotalSeats():schedule.getTotalSeats());
        schedule.setStatus(updateRequest.getStatus()!= null ? updateRequest.getStatus() : ScheduleStatus.AVAILABLE);
        Schedule saved = scheduleRepository.save(schedule);
        return new ScheduleResponse(saved);
    }

    @Override
    public ScheduleResponse deleteSchedule(int id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new HttpNotFound("Schedule not found"));
        ScheduleResponse scheduleResponse = new ScheduleResponse(schedule);
        scheduleRepository.delete(schedule);
        return scheduleResponse;
    }


}
