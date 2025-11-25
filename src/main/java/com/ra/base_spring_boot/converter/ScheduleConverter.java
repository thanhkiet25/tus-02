package com.ra.base_spring_boot.converter;

import com.ra.base_spring_boot.dto.req.ScheduleRequest;
import com.ra.base_spring_boot.dto.resp.ScheduleResponse;
import com.ra.base_spring_boot.model.Bus;
import com.ra.base_spring_boot.model.Route;
import com.ra.base_spring_boot.model.Schedule;
import org.springframework.stereotype.Component;

@Component
public class ScheduleConverter {
   public ScheduleResponse toScheduleResponse(Schedule schedule) {
      String routeNames = schedule.getRoute().getDepartureStation().getName()
              +" -> "+schedule.getRoute().getArrivalStation().getName();
      return ScheduleResponse.builder()
              .scheduleId(schedule.getId())
              .routeName(routeNames)
              .busName(schedule.getBus().getName())
              .departureTime(schedule.getDepartureTime())
              .arrivalTime(schedule.getArrivalTime())
              .availableSeat(schedule.getAvailableSeat())
              .totalSeats(schedule.getTotalSeats())
              .status(schedule.getStatus())
              .build();
   }
   public Schedule toSchedule(ScheduleRequest scheduleRequest, Route route, Bus bus) {
      return Schedule.builder()
              .route(route)
              .bus(bus)
              .departureTime(scheduleRequest.getDeparture())
              .arrivalTime(scheduleRequest.getArrival())
              .availableSeat(scheduleRequest.getAvailableSeat())
              .totalSeats(scheduleRequest.getTotalSeats())
              .status(scheduleRequest.getStatus())
              .build();

   }

}
