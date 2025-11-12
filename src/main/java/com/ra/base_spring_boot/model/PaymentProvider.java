package com.ra.base_spring_boot.model;

import com.ra.base_spring_boot.model.base.BaseObject;
import com.ra.base_spring_boot.model.constants.ProviderType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "payment_providers")
public class PaymentProvider extends BaseObject {
    @Column(name = "provider_name")
    private String providerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type")
    private ProviderType providerType;

    @Column(name = "api_endpoint")
    private String apiEndpoint;

    @OneToMany(mappedBy = "paymentProvider")
    private Set<Payment> payments = new HashSet<>();
}
