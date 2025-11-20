package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StationResponse {
    private int id;

    private String name;

    private String image;

    private String wallpaper;

    private String descriptions;

    private String location;

    private String departureRoutes;

    private String arrivalRoutes;

    public StationResponse(Station station){
        this.id = station.getId();
        this.name = station.getName();
        this.image = station.getImage();
        this.wallpaper = station.getWallpaper();
        this.descriptions = station.getDescriptions();
        this.location = station.getLocation();
        this.arrivalRoutes = station.getArrivalRoutes().toString();
        this.departureRoutes = station.getDepartureRoutes().toString();
    }

}
