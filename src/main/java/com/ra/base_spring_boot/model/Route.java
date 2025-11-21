package com.ra.base_spring_boot.model;

import com.ra.base_spring_boot.model.base.BaseObject;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "routes")
public class Route extends BaseObject {

    private Double price;

    private Integer duration;

    private Integer distance;

    @ManyToOne
    @JoinColumn(name = "departure_station_id")
    private Station departureStation;

    @ManyToOne
    @JoinColumn(name = "arrival_station_id")
    private Station arrivalStation;

    @OneToMany(mappedBy = "route")
    @Builder.Default
    private Set<Schedule> schedules = new HashSet<>();

    @OneToMany(mappedBy = "route")
    @Builder.Default
    private Set<CancellationPolicy> cancellationPolicies = new HashSet<>();
}
