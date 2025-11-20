package com.ra.base_spring_boot.dto.resp;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PopularRouteResponse {
    private Integer routeId;
    private String departureStation;
    private String arrivalStation;
    private Long totalTickets;
}
