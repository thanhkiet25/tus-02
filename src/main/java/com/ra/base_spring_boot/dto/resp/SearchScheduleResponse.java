package com.ra.base_spring_boot.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchScheduleResponse {
private Integer scheduleId;
private String departureStation;
private String arrivalStation;
private String busCompanyName;
private String busName;
private String busImage;
private Integer capacity;
private Integer availableSeat;
private Double price;
private LocalDateTime departureTime;
private LocalDateTime arrivalTime;
}
