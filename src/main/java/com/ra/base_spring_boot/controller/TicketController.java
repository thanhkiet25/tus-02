package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.resp.TicketResponse;
import com.ra.base_spring_boot.services.impl.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @GetMapping("/tickets/search")
    public ResponseEntity<ResponseWrapper<TicketResponse>> searchTicket(
            @RequestParam Integer id,
            @RequestParam String phone) {

        try {
            TicketResponse result = ticketService.searchTicket(id, phone);

            return ResponseEntity.ok(
                    ResponseWrapper.<TicketResponse>builder()
                            .status(HttpStatus.OK)
                            .code(HttpStatus.OK.value())
                            .data(result)
                            .build()
            );

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(
                    ResponseWrapper.<TicketResponse>builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .code(HttpStatus.BAD_REQUEST.value())
                            .data(null)
                            .build()
            );
        }
    }


}
