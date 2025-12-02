package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.resp.StatisticPaymentProviderResponse;
import com.ra.base_spring_boot.repository.IPaymentRepository;
import com.ra.base_spring_boot.services.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {
    private final IPaymentRepository paymentRepository;
    @Override
    public List<StatisticPaymentProviderResponse> statisticByPaymentProvider() {
        List<Object[]> paymentProviderList = paymentRepository.statisticByPaymentProvider();
        return paymentProviderList.stream().map(
                row -> {
                    Double total = row[2] != null?
                            ((Number)row[2]).doubleValue()
                            :0.0;
                  return StatisticPaymentProviderResponse.builder()
                            .providerName((String) row[0])
                            .totalTransactions(((Number) row[1]).intValue())
                            .totalAmount(total)
                            .build();
                }).toList();
    }
}
