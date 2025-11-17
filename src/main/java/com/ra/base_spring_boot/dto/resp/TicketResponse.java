package com.ra.base_spring_boot.dto.resp;

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
    private int ticketId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private SeatType seatType;
    private Double price;
    private TicketStatus status;
}
