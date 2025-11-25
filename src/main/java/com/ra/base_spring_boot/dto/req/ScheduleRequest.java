package com.ra.base_spring_boot.dto.req;

import com.ra.base_spring_boot.model.Bus;
import com.ra.base_spring_boot.model.Route;
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
public class ScheduleRequest {
    private Integer routeId;
    private Integer busId;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private Integer availableSeat;
    private Integer totalSeats;
    private ScheduleStatus status;
}
