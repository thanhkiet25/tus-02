package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.Bus;
import com.ra.base_spring_boot.model.Seat;
import com.ra.base_spring_boot.model.Ticket;
import com.ra.base_spring_boot.model.constants.SeatStatus;
import com.ra.base_spring_boot.model.constants.SeatType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SeatListResponse {

    private int id;

    private String seatNumber;

    private SeatType seatType;

    private SeatStatus status;

    private Double priceForSeatType;

    private int busId;

    private String busName;

    private String busCompanyName;

    public SeatListResponse(Seat seat){
        this.id = seat.getId();
        this.seatNumber = seat.getSeatNumber();
        this.seatType = seat.getSeatType();
        this.status = seat.getStatus();
        this.priceForSeatType = seat.getPriceForSeatType();
        this.busId = seat.getBus().getId();
        this.busName = seat.getBus().getName();
        this.busCompanyName = seat.getBus().getBusCompany().getCompanyName();
    }

}
