package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.StatisticPaymentProviderResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IPaymentService {
    List<StatisticPaymentProviderResponse> statisticByPaymentProvider();

    String createVnPayUrl(Integer paymentId, long amount, HttpServletRequest request);

    int orderReturn(HttpServletRequest request);
}
