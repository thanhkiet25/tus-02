package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.PaymentProvider;
import com.ra.base_spring_boot.model.constants.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentProviderResponse {
    private int id;

    private String providerName;

    private ProviderType providerType;

    private String apiEndpoint;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    public PaymentProviderResponse(PaymentProvider paymentProvider) {
         this.id = paymentProvider.getId();
         this.providerName = paymentProvider.getProviderName();
         this.providerType = paymentProvider.getProviderType();
         this.apiEndpoint = paymentProvider.getApiEndpoint();
         this.createdDate = paymentProvider.getCreatedAt();
         this.updatedDate = paymentProvider.getUpdatedAt();
    }
}
