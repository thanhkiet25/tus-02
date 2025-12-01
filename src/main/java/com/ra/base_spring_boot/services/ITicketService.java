package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.TicketResponse;
import com.ra.base_spring_boot.model.constants.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ITicketService {
    TicketResponse searchTicket( Integer id, String phone);

    Page<TicketResponse> getAllAndSearchTicket(String keyword, Pageable pageable);

    Page<TicketResponse> getTicketByCompany( Integer companyId, Pageable pageable);

    Page<TicketResponse> getTicketByStatus(TicketStatus status, Pageable pageable);

}
