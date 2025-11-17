package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.resp.TicketResponse;
import com.ra.base_spring_boot.model.Ticket;
import com.ra.base_spring_boot.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    public TicketResponse searchTicket(Integer id, String phone) {
        Ticket ticket = ticketRepository.searchTicket(id, phone)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé với thông tin cung cấp."));

        return TicketResponse.builder()
                .ticketId(ticket.getId())
                .departureTime(ticket.getDepartureTime())
                .arrivalTime(ticket.getArrivalTime())
                .price(ticket.getPrice())
                .status(ticket.getStatus())
                .build();
    }

}
