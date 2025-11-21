package com.ra.base_spring_boot.model;

import com.ra.base_spring_boot.model.base.BaseObject;
import com.ra.base_spring_boot.model.constants.SeatStatus;
import com.ra.base_spring_boot.model.constants.SeatType;
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
@Table(name = "seats")
public class Seat extends BaseObject {
    @Column(name = "seat_number", length = 20)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type")
    private SeatType seatType;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @Column(name = "price_for_seat_type")
    private Double priceForSeatType;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @OneToMany(mappedBy = "seat")
    @Builder.Default
    private Set<Ticket> tickets = new HashSet<>();
}
