package com.ra.base_spring_boot.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RouteRequest {
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer duration;

    @NotNull(message = "Distance is required")
    @Positive(message = "Distance must be greater than 0")
    private Integer distance;

    @NotNull(message = "Departure station ID is required")
    @Positive(message = "Departure station ID must be greater than 0")
    private Integer departureStationId;

    @NotNull(message = "Arrival station ID is required")
    @Positive(message = "Arrival station ID must be greater than 0")
    private Integer arrivalStationId;
}
