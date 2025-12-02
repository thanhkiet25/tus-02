package com.ra.base_spring_boot.dto.req;

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
public class ScheduleUpdateRequest {
    private Integer busId;
    private Integer routeId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer availableSeat;
    private Integer totalSeats;
    private ScheduleStatus status;
}
