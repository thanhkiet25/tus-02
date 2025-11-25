package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.Route;
import com.ra.base_spring_boot.model.Schedule;
import com.ra.base_spring_boot.model.constants.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScheduleResponse {
    private Integer scheduleId;
    private String busName;
    private String routeName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer availableSeat;
    private Integer totalSeats;
    private ScheduleStatus status;
    public ScheduleResponse(Schedule schedule) {
        String routeName = schedule.getRoute().getDepartureStation().getName() + " -> " + schedule.getRoute().getArrivalStation().getName();
        this.scheduleId = schedule.getId();
        this.busName = schedule.getBus().getBusName();
        this.routeName = routeName;
        this.departureTime = schedule.getDepartureTime();
        this.arrivalTime = schedule.getArrivalTime();
        this.availableSeat = schedule.getAvailableSeat();
        this.totalSeats = schedule.getTotalSeats();
        this.status = schedule.getStatus();
    }
}
