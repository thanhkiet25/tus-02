package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.req.PaymentProviderRequest;
import com.ra.base_spring_boot.dto.resp.PaymentProviderListResponse;
import com.ra.base_spring_boot.dto.resp.PaymentProviderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface IPaymentProviderService {
    Page<PaymentProviderListResponse> findAllByPaymentProviderAndSearchByProviderName( String searchProviderName,Pageable pageable);

    PaymentProviderResponse createPaymentProvider(PaymentProviderRequest paymentProviderRequest);

    void deletePaymentProvider( Integer paymentProviderId);


}
