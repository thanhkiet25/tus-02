package com.ra.base_spring_boot.services.impl;
import com.ra.base_spring_boot.dto.req.PaymentProviderRequest;
import com.ra.base_spring_boot.dto.resp.PaymentProviderListResponse;
import com.ra.base_spring_boot.dto.resp.PaymentProviderResponse;
import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.PaymentProvider;
import com.ra.base_spring_boot.repository.IPaymentProviderRepository;
import com.ra.base_spring_boot.services.IPaymentProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProviderServiceImpl implements IPaymentProviderService {

    private final IPaymentProviderRepository paymentProviderRepository;

    @Override
    public Page<PaymentProviderListResponse> findAllByPaymentProviderAndSearchByProviderName(String searchProviderName, Pageable pageable) {
        Page<PaymentProvider> listProvider = paymentProviderRepository.findAllPaymentProviderByProviderNameContainingIgnoreCase(searchProviderName, pageable);
        if (listProvider == null ) {
            throw new RuntimeException("ListProvider is empty!");
        }
        return listProvider.map(PaymentProviderListResponse::new);
    }

    @Override
    public PaymentProviderResponse createPaymentProvider(PaymentProviderRequest paymentProviderRequest) {
        if(paymentProviderRepository.existsPaymentProviderByProviderName(paymentProviderRequest.getProviderName())) {
            throw new HttpConflict("Payment provider name is existed!");
        }
        PaymentProvider paymentProvider = new PaymentProvider();
        paymentProvider.setProviderName(paymentProviderRequest.getProviderName());
        paymentProvider.setProviderType(paymentProviderRequest.getProviderType());
        paymentProvider.setApiEndpoint(paymentProviderRequest.getApiEndpoint());
        PaymentProvider newPaymentProvider = paymentProviderRepository.save(paymentProvider);
        return new PaymentProviderResponse(newPaymentProvider);
    }

    @Override
    public void deletePaymentProvider(Integer paymentProviderId) {
        PaymentProvider paymentProvider = paymentProviderRepository.findById(paymentProviderId).orElseThrow(()-> new HttpNotFound("Payment Provider id " + paymentProviderId +"  Not Found!"));
        if(paymentProviderRepository.hasRelatedPayment(paymentProviderId)) {
            throw new HttpConflict("Payment provider is related payment, cant not delete!");
        }else {
            paymentProviderRepository.delete(paymentProvider);
        }
    }


}
