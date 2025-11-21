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
public class    Station extends BaseObject {
    @Column(nullable = false, unique = true)
    private String name;

    private String image;

    private String wallpaper;

    @Column(columnDefinition = "longtext")
    private String descriptions;

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "departureStation")
    @Builder.Default
    private Set<Route> departureRoutes = new HashSet<>();

    @OneToMany(mappedBy = "arrivalStation")
    @Builder.Default
    private Set<Route> arrivalRoutes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "bus_station",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "bus_id")
    )
    @Builder.Default
    private Set<Bus> buses = new HashSet<>();

}
