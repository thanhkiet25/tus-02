package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.CancellationPolicy;
import com.ra.base_spring_boot.model.Route;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CancellationPolicyResponse {
    private int id;

    private String descriptions;

    private Integer cancellationTimeLimit;

    private Integer refundPercentage;

    private Integer routeId;

    private String departureStation;

    private String arrivalStation;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    public CancellationPolicyResponse(CancellationPolicy cancellationPolicy) {
        this.id = cancellationPolicy.getId();
        this.descriptions = cancellationPolicy.getDescriptions();
        this.cancellationTimeLimit = cancellationPolicy.getCancellationTimeLimit();
        this.refundPercentage = cancellationPolicy.getRefundPercentage();
        this.routeId = cancellationPolicy.getRoute().getId();
        this.departureStation = cancellationPolicy.getRoute().getDepartureStation().getName();
        this.arrivalStation = cancellationPolicy.getRoute().getArrivalStation().getName();
        this.createdDate = cancellationPolicy.getCreatedAt();
        this.updatedDate = cancellationPolicy.getUpdatedAt();
    }

}
