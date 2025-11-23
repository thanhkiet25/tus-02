package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.Route;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RouteListResponse {
    private int id;

    private Double price;

    private Integer duration;

    private Integer distance;

    private String departureStation;

    private String arrivalStation;

    public RouteListResponse(Route route) {
        this.id = route.getId();
        this.price = route.getPrice();
        this.duration = route.getDuration();
        this.distance = route.getDistance();
        this.departureStation = route.getDepartureStation().getName();
        this.arrivalStation = route.getArrivalStation().getName();
    }
}
