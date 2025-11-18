package com.ra.base_spring_boot.dto.resp;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchScheduleDTO {
private Integer scheduleId;
private String descriptionStation;
private String arrivalStation;
private LocalDateTime departureTime;
}
