package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.BookingRequest;
import com.ra.base_spring_boot.dto.resp.BookingHistoryResponse;
import com.ra.base_spring_boot.model.constants.TicketStatus;
import com.ra.base_spring_boot.services.IBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/bookings")
@RequiredArgsConstructor
@Tag(name = "User Booking", description = "Operations for booking and history")
public class BookingController {
    private final IBookingService bookingService;

    @PostMapping
    @Operation(summary = "Book tickets", description = "Create new booking")
    public ResponseEntity<?> bookTicket(@Valid @RequestBody BookingRequest request) {

        List<BookingHistoryResponse> result = bookingService.bookTicket(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(201)
                        .data(result)
                        .build()
        );
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel ticket", description = "Cancel a booked ticket by ID")
    public ResponseEntity<?> cancelTicket(@PathVariable Integer id) {

        BookingHistoryResponse cancelledTicket = bookingService.cancelTicket(id);

        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(cancelledTicket)
                        .build()
        );
    }

    @GetMapping("/history")
    @Operation(summary = "Get booking history", description = "Get history with optional filters")
    public ResponseEntity<?> getHistory(
            @RequestParam(required = false) TicketStatus status,
            @RequestParam(required = false) String companyName,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(bookingService.getHistory(status, companyName, pageable))
                        .build()
        );
    }
}