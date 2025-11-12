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
@Table(name = "buses")
public class Bus extends BaseObject {
    private String name;

    @Column(columnDefinition = "text")
    private String descriptions;

    @Column(name = "license_plate", length = 50)
    private String licensePlate;

    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private BusCompany busCompany;

    @ManyToMany(mappedBy = "buses")
    private Set<Station> stations = new HashSet<>();

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BusImage> images = new HashSet<>();

    @OneToMany(mappedBy = "bus")
    private Set<Schedule> schedules = new HashSet<>();

    @OneToMany(mappedBy = "bus")
    private Set<Seat> seats = new HashSet<>();

    @OneToMany(mappedBy = "bus")
    private Set<BusReview> reviews = new HashSet<>();
}
