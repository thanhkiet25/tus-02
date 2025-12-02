package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.constants.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RevenueDetailResponse {
    private int routeId;
    private String busCompanyName;
    private String seatType;
    private double amount;
    private LocalDateTime paymentDate;
    private PaymentMethod paymentMethod;

}
