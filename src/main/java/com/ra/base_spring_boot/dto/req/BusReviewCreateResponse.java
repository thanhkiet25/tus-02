package com.ra.base_spring_boot.dto.req;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BusReviewCreateResponse {
    @NotNull(message = "vui long nhap rating")
    private Integer rating;
    @NotNull(message = "vui long nhap review")
    private String review;
    @NotNull(message = "vui long nhap ma busId")
    private int busId;
}
