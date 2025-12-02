package com.ra.base_spring_boot.services.impl;
import com.ra.base_spring_boot.dto.req.ScheduleCreateRequest;
import com.ra.base_spring_boot.dto.req.ScheduleUpdateRequest;
import com.ra.base_spring_boot.dto.resp.ScheduleResponse;
import com.ra.base_spring_boot.dto.resp.SearchScheduleResponse;
import com.ra.base_spring_boot.dto.resp.SeatAvailabilityResponse;
import com.ra.base_spring_boot.model.Schedule;
import com.ra.base_spring_boot.model.Seat;
import com.ra.base_spring_boot.model.constants.SeatStatus;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements IScheduleService {
    private final IScheduleRepository scheduleRepository;
    private final IRouteRepository routeRepository;
    private final IBusRepository busRepository;
    private final ISeatRepository seatRepository;
    private final ITicketRepository ticketRepository;

    @Override
    public List<SearchScheduleResponse> searchSchedule(Long fromId, Long toId, LocalDate date) {
        if (fromId == null || toId == null) {
            throw new HttpBadRequest("fromId and toId can't be null!");
        }
        if (date == null) {
            throw new HttpBadRequest("date can't be null!");
        }

        List<SearchScheduleResponse> results = scheduleRepository.searchSchedule(fromId, toId, date);

        if (results.isEmpty()) {
            throw new HttpNotFound("Schedule not found!");
        }
        return results;
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

        LocalDateTime departureTime = scheduleRequest.getDepartureTime();
        LocalDateTime arrivalTime = scheduleRequest.getArrivalTime();

       int conflict = scheduleRepository.overLappingSchedules(
               bus.getId(),
               departureTime,
              arrivalTime);
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
        boolean isModified = false;
        if(updateRequest.getBusId()!= null){
            Bus bus = busRepository.findById(updateRequest.getBusId()).orElseThrow(() -> new HttpNotFound("Bus is not found"));
            schedule.setBus(bus);
            isModified = true;
       }

        if(updateRequest.getRouteId()!= null){
            Route route = routeRepository.findById(updateRequest.getRouteId()).orElseThrow(() -> new HttpNotFound("Route is not found"));
            schedule.setRoute(route);
            isModified = true;
        }

        LocalDateTime newDepartureTime = updateRequest.getDepartureTime() != null ? updateRequest.getDepartureTime() : schedule.getDepartureTime() ;
        LocalDateTime newArrivalTime =updateRequest.getArrivalTime() != null ? updateRequest.getArrivalTime() : schedule.getArrivalTime();

        boolean isTimeChanged =
                !newDepartureTime.equals(schedule.getDepartureTime()) ||
                !newArrivalTime.equals(schedule.getArrivalTime());

        Integer currenBusId = updateRequest.getBusId() != null
                ? updateRequest.getBusId()
                :schedule.getBus().getId();
       if (isTimeChanged) {
           int conflict = scheduleRepository.overLappingSchedules(
                   currenBusId,
                   schedule.getId(),
                   newDepartureTime,
                   newArrivalTime
           );
           if (conflict>0) {
               throw new HttpConflict("this bus is already has a schedule that overlaps this time");
           }
           schedule.setDepartureTime(newDepartureTime);
           schedule.setArrivalTime(newArrivalTime);
           isModified = true;
       }
        if(updateRequest.getAvailableSeat()!=null){
            schedule.setAvailableSeat(updateRequest.getAvailableSeat());
            isModified = true;
        }
        if(updateRequest.getTotalSeats()!=null){
            schedule.setTotalSeats(updateRequest.getTotalSeats());
            isModified = true;
        }
        if(updateRequest.getStatus()!=null){
            schedule.setStatus(updateRequest.getStatus());
            isModified = true;
        }
        if(!isModified) {
            throw new HttpBadRequest("No changes detected.");
        }
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

    @Override
    public ScheduleResponse getScheduleById(int id) {
        Schedule schedule= scheduleRepository.findById(id).orElseThrow(()-> new HttpNotFound("Schedule not found"));
    return   new ScheduleResponse(schedule);
    }

    @Override
    public List<SeatAvailabilityResponse> getSeatsByScheduleId(Integer scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new HttpNotFound("Schedule not found"));

        List<Seat> allSeats = seatRepository.findAllByBusId(schedule.getBus().getId());

        List<Integer> bookedSeatIds = ticketRepository.findBookedSeatIdsByScheduleId(scheduleId);

        return allSeats.stream().map(seat -> {
            boolean isBooked = bookedSeatIds.contains(seat.getId());

            return SeatAvailabilityResponse.builder()
                    .seatId(seat.getId())
                    .seatNumber(seat.getSeatNumber())
                    .price(schedule.getRoute().getPrice() + seat.getPriceForSeatType())
                    .status(isBooked ? SeatStatus.BOOKED : SeatStatus.AVAILABLE)
                    .build();
        }).collect(Collectors.toList());
    }


}
