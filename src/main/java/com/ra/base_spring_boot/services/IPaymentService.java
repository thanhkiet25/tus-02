package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.StatisticPaymentProviderResponse;

import java.util.List;

public interface IPaymentService {
    List<StatisticPaymentProviderResponse> statisticByPaymentProvider();
}
