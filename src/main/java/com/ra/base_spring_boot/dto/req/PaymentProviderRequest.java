package com.ra.base_spring_boot.dto.req;

import com.ra.base_spring_boot.model.constants.ProviderType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentProviderRequest {

    @NotBlank(message = "provider name not null.")
    private String providerName;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "provider type not null")
    private ProviderType providerType;
    @NotBlank(message = "api endpoint not null")
    private String apiEndpoint;
}
