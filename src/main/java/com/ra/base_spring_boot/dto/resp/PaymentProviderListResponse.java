package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.Payment;
import com.ra.base_spring_boot.model.PaymentProvider;
import com.ra.base_spring_boot.model.constants.ProviderType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentProviderListResponse {

    private String providerName;

    private ProviderType providerType;

    private String apiEndpoint;

    public PaymentProviderListResponse(PaymentProvider paymentProvider) {
        this.providerName = paymentProvider.getProviderName();
        this.providerType = paymentProvider.getProviderType();
        this.apiEndpoint = paymentProvider.getApiEndpoint();
    }

}
