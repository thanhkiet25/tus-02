package com.ra.base_spring_boot.model;

import com.ra.base_spring_boot.model.base.BaseObject;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "stations")
public class Station extends BaseObject {
    @Column(nullable = false)
    private String name;

    private String image;

    private String wallpaper;

    @Column(columnDefinition = "longtext")
    private String descriptions;

    @Column
    private String location;

    @OneToMany(mappedBy = "departureStation")
    private Set<Route> departureRoutes = new HashSet<>();

    @OneToMany(mappedBy = "arrivalStation")
    private Set<Route> arrivalRoutes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "bus_station",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "bus_id")
    )
    private Set<Bus> busses = new HashSet<>();

}
