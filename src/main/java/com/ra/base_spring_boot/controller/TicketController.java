package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.resp.TicketResponse;
import com.ra.base_spring_boot.services.impl.TicketServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TicketController {
    private final TicketServiceImpl ticketService;
    @GetMapping("/tickets/search")
    public ResponseEntity<?> searchTicket(
            @RequestParam Integer id,
            @RequestParam String phone) {
        TicketResponse ticket = ticketService.searchTicket(id, phone);
        return new ResponseEntity<>(ResponseWrapper
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(ticket)
                .build(), HttpStatus.OK);

    }


}
