package com.ra.base_spring_boot.dto.req;

import com.ra.base_spring_boot.model.constants.ScheduleStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScheduleCreateRequest {
    @NotNull(message = "busId is required")
    private Integer busId;
    @NotNull(message = "routeId is required")
    private Integer routeId;
    @NotNull(message = "departureTime is required")
    private LocalDateTime departureTime;
    @NotNull(message = "arrivalTime is required")
    private LocalDateTime arrivalTime;
    @NotNull(message = "availableSeat is required")
    private Integer availableSeat;
    @NotNull(message = "totalSeats is required")
    private Integer totalSeats;
    private ScheduleStatus status;
}
