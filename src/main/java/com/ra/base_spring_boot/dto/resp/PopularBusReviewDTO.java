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
public class PopularBusReviewDTO {
    private  Integer reviewId;
    private Integer rating;
    private String review;
    private String busName;
    private String departureStation;
    private String arrivalStation;
    private LocalDateTime createdAt;
}
