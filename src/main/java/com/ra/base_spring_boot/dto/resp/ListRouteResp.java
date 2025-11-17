package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.Route;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ListRouteResp {
    private int id;

    private Double price;

    private Integer duration;

    private Integer distance;

    private String departureStation;

    private String arrivalStation;

    public ListRouteResp(Route route) {
        this.id = route.getId();
        this.price = route.getPrice();
        this.duration = route.getDuration();
        this.distance = route.getDistance();
        this.departureStation = route.getDepartureStation().getName();
        this.arrivalStation = route.getArrivalStation().getName();

    }
}
