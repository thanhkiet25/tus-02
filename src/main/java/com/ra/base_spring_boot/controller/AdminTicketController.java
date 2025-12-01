package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.resp.TicketResponse;
import com.ra.base_spring_boot.model.constants.TicketStatus;
import com.ra.base_spring_boot.services.impl.TicketServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/tickets")
@RequiredArgsConstructor
public class AdminTicketController {
    private final TicketServiceImpl ticketService;

    @GetMapping
    public ResponseEntity<?> getAllTickets(@RequestParam(required = false) String keyword,
                                           Pageable pageable) {
        Page<TicketResponse> ticketResponses = ticketService.getAllAndSearchTicket(keyword, pageable);
        return new ResponseEntity<>(ResponseWrapper
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(ticketResponses)
                .build(), HttpStatus.OK);
    }
    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getTicketByCompany(@PathVariable Integer companyId, Pageable pageable) {
        Page<TicketResponse> ticketResponses = ticketService.getTicketByCompany(companyId, pageable );
        return new ResponseEntity<>(ResponseWrapper
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(ticketResponses)
                .build(), HttpStatus.OK);
    }
    @GetMapping("/status")
    public ResponseEntity<?> getTicketByStatus(@RequestParam TicketStatus status, Pageable pageable) {
        Page<TicketResponse> ticketResponses = ticketService.getTicketByStatus(status, pageable );
        return new ResponseEntity<>(ResponseWrapper
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(ticketResponses)
                .build(), HttpStatus.OK);
    }
}
