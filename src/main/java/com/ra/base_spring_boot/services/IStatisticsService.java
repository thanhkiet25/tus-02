package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.RevenueStatisticsResponse;
import com.ra.base_spring_boot.dto.resp.StatisticsLoyalCustomerResponse;
import com.ra.base_spring_boot.dto.resp.StatisticsNewCustomerResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IStatisticsService {
    RevenueStatisticsResponse getRevenueStatisticsByRouteAndBusCompanyForMonth(Integer routeId, String busCompanyName, Integer month, Integer year);

    RevenueStatisticsResponse getRevenueStatisticsByRouteAndBusCompanyForYear(Integer routeId, String busCompanyName, Integer year);

    RevenueStatisticsResponse getRevenueStatisticsByWeek(Integer routeId, String busCompanyName);

    LocalDateTime[] getStartAndEndDateOfCurrentWeek();

    Double calculateWeeklyRevenue(Integer routeId, String busCompanyName);

    StatisticsNewCustomerResponse getNewCustomerStatistics();

    StatisticsLoyalCustomerResponse getLoyalCustomerStatistics();
}
