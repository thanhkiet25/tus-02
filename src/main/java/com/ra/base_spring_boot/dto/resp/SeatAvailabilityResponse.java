package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.constants.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SeatAvailabilityResponse {
    private Integer seatId;
    private String seatNumber;
    private Double price;
    private SeatStatus status;
}
