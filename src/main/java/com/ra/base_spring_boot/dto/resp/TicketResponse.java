package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.Bus;
import com.ra.base_spring_boot.model.Ticket;
import com.ra.base_spring_boot.model.constants.SeatType;
import com.ra.base_spring_boot.model.constants.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketResponse {
    private Integer id;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String departureStationName;
    private String arrivalStationName;
    private String busCompanyName;
    private String busName;
    private String seatNumber;
    private SeatType seatType;
    private Double price;
    private TicketStatus status;
    private Integer scheduleId;
    private Integer seatId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TicketResponse(Ticket ticket) {
        this.id = ticket.getId();
        this.departureTime = ticket.getDepartureTime();
        this.arrivalTime = ticket.getArrivalTime();
        this.departureStationName = ticket.getSchedule().getRoute().getDepartureStation().getName();
        this.arrivalStationName = ticket.getSchedule().getRoute().getArrivalStation().getName();
        this.busCompanyName = ticket.getSchedule().getBus().getBusCompany().getCompanyName();
        this.busName = ticket.getSchedule().getBus().getBusName();
        this.seatNumber = ticket.getSeat().getSeatNumber();
        this.seatType = ticket.getSeatType();
        this.price = ticket.getPrice();
        this.status = ticket.getStatus();
        this.scheduleId = ticket.getSchedule().getId();
        this.seatId = ticket.getSeat().getId();
        this.createdAt = ticket.getCreatedAt();
        this.updatedAt = ticket.getUpdatedAt();
    }


}
