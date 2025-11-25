package com.ra.base_spring_boot.dto.resp;

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
    private String routeName;
    private String busName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int availableSeat;
    private int totalSeats;
    private ScheduleStatus status;
}
