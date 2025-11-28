package com.ra.base_spring_boot.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingRequest {
    @NotNull(message = "Schedule ID is required")
    private Integer scheduleId;

    @NotEmpty(message = "Must select at least one seat")
    private List<Integer> seatIds;

    @NotNull(message = "Payment Provider ID is required")
    private Integer paymentProviderId;
}
