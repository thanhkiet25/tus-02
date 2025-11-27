package com.ra.base_spring_boot.dto.req;

import com.ra.base_spring_boot.model.Route;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CancellationPolicyRequest {
    @NotBlank(message = "Description is not null")
    private String descriptions;
    @NotNull(message = "Cancellation time is limit not null")
    private Integer cancellationTimeLimit;
    @NotNull(message = "refund percentage is not null")
    private Integer refundPercentage;
    @NotNull(message = "Route id is not null")
    private int routeId;
}
