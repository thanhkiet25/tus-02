package com.ra.base_spring_boot.dto.req;

import com.ra.base_spring_boot.model.Bus;
import com.ra.base_spring_boot.model.Ticket;
import com.ra.base_spring_boot.model.constants.SeatStatus;
import com.ra.base_spring_boot.model.constants.SeatType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SeatRequest {
    @NotBlank(message = "seat number is not null")
    private String seatNumber;
    @NotNull(message = "seat type is not null")
    private SeatType seatType;
    @NotNull(message = "seat status is not null")
    private SeatStatus status;
    @NotNull(message = "seat price is not null")
    private Double priceForSeatType;
    @NotNull(message = "bus id is not null")
    private int busId;
}
