package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.Ticket;
import com.ra.base_spring_boot.model.constants.PaymentStatus;
import com.ra.base_spring_boot.model.constants.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingHistoryResponse {
    private Integer ticketId;
    private String routeName;
    private String busCompanyName;
    private String busNumber;
    private String seatNumber;
    private LocalDateTime departureTime;
    private Double price;
    private TicketStatus ticketStatus;
    private PaymentStatus paymentStatus;
    private LocalDateTime bookingDate;

    public BookingHistoryResponse(Ticket ticket) {
        this.ticketId = ticket.getId();

        if (ticket.getSchedule() != null && ticket.getSchedule().getRoute() != null) {
            this.routeName = ticket.getSchedule().getRoute().getDepartureStation().getName()
                    + " - " + ticket.getSchedule().getRoute().getArrivalStation().getName();
            this.departureTime = ticket.getSchedule().getDepartureTime();

            if (ticket.getSchedule().getBus() != null) {
                this.busNumber = ticket.getSchedule().getBus().getLicensePlate();
                if (ticket.getSchedule().getBus().getBusCompany() != null) {
                    this.busCompanyName = ticket.getSchedule().getBus().getBusCompany().getCompanyName();
                }
            }
        }

        if (ticket.getSeat() != null) {
            this.seatNumber = ticket.getSeat().getSeatNumber();
        }

        this.price = ticket.getPrice();
        this.ticketStatus = ticket.getStatus();

        if (ticket.getPayment() != null) {
            this.paymentStatus = ticket.getPayment().getStatus();
        }

        this.bookingDate = ticket.getCreatedAt();
    }
}
