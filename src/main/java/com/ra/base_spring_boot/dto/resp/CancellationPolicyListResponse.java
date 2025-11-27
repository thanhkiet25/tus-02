package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.CancellationPolicy;
import com.ra.base_spring_boot.model.Route;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CancellationPolicyListResponse {
    private int id;

    private String descriptions;

    private Integer cancellationTimeLimit;

    private Integer refundPercentage;

    private String departureStation;

    private String arrivalStation;

    public CancellationPolicyListResponse(CancellationPolicy cancellationPolicy) {
        this.id = cancellationPolicy.getId();
        this.descriptions = cancellationPolicy.getDescriptions();
        this.cancellationTimeLimit = cancellationPolicy.getCancellationTimeLimit();
        this.refundPercentage = cancellationPolicy.getRefundPercentage();
        this.departureStation = cancellationPolicy.getRoute().getDepartureStation().getName();
        this.arrivalStation = cancellationPolicy.getRoute().getArrivalStation().getName();
    }
}
