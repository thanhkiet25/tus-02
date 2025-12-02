package com.ra.base_spring_boot.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StatisticPaymentProviderResponse {
    private String providerName;
    private Integer totalTransactions;
    private Double totalAmount;
}
