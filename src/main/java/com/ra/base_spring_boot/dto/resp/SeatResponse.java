package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.Seat;
import com.ra.base_spring_boot.model.constants.SeatStatus;
import com.ra.base_spring_boot.model.constants.SeatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SeatResponse {
    private int id;

    private String seatNumber;

    private SeatType seatType;

    private SeatStatus status;

    private Double priceForSeatType;

    private int busId;

    private String busName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public SeatResponse(Seat seat){
        this.id = seat.getId();
        this.seatNumber = seat.getSeatNumber();
        this.seatType = seat.getSeatType();
        this.status = seat.getStatus();
        this.priceForSeatType = seat.getPriceForSeatType();
        this.busId = seat.getBus().getId();
        this.busName = seat.getBus().getBusName();
        this.createdAt = seat.getCreatedAt();
        this.updatedAt = seat.getUpdatedAt();
    }
}
