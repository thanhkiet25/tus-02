package com.ra.base_spring_boot.model;

import com.ra.base_spring_boot.model.base.BaseObject;
import com.ra.base_spring_boot.model.constants.ScheduleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "schedules")
public class Schedule extends BaseObject {
    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "available_seat")
    private Integer availableSeat;

    @Column(name = "total_seats")
    private Integer totalSeats;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @OneToMany(mappedBy = "schedule")
    @Builder.Default
    private Set<Ticket> tickets = new HashSet<>();
}
