package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.req.BookingRequest;
import com.ra.base_spring_boot.dto.resp.BookingHistoryResponse;
import com.ra.base_spring_boot.model.constants.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookingService {
    List<BookingHistoryResponse> bookTicket(BookingRequest request);
    BookingHistoryResponse cancelTicket(Integer ticketId);
    Page<BookingHistoryResponse> getHistory(TicketStatus status, String companyName, Pageable pageable);
}
