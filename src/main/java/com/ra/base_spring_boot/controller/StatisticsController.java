package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.resp.RevenueStatisticsResponse;
import com.ra.base_spring_boot.dto.resp.StatisticsLoyalCustomerResponse;
import com.ra.base_spring_boot.dto.resp.StatisticsNewCustomerResponse;
import com.ra.base_spring_boot.services.IAuthService;
import com.ra.base_spring_boot.services.IStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/statistics")
public class StatisticsController {

    private final IStatisticsService statisticsService;


    @GetMapping("/revenue-by-month")
    @Operation(summary = "revenue by month", description = "statistics revenue by month with route id and bus company name")
    public ResponseEntity<?> revenueStatisticsByRouteIdAndBusCompanyNameForMonth(
            @RequestParam Integer routeId,
            @RequestParam String busCompanyName,
            @RequestParam Integer month,
            @RequestParam Integer year) {
        RevenueStatisticsResponse response = statisticsService.getRevenueStatisticsByRouteAndBusCompanyForMonth(routeId, busCompanyName, month, year);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(response)
                        .build()
                , HttpStatus.OK);

    }

    @GetMapping("/revenue-by-year")
    @Operation(summary = "revenue by year", description = "statistics revenue by year with route id and bus company name")
    public ResponseEntity<?> revenueStatistics(@RequestParam Integer routeId,
                                               @RequestParam String busCompanyName,
                                               @RequestParam Integer year) {
        RevenueStatisticsResponse response = statisticsService.getRevenueStatisticsByRouteAndBusCompanyForYear(routeId, busCompanyName, year);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(response)
                        .build()
                , HttpStatus.OK);

    }

    @GetMapping("/revenue-by-current-week")
    @Operation(summary = "revenue by current week", description = "statistics revenue by current week with route id and bus company name")
    public ResponseEntity<?> revenueStatisticsByCurrentWeek(
            @RequestParam Integer routeId,
            @RequestParam String busCompanyName
    ) {
        RevenueStatisticsResponse response = statisticsService.getRevenueStatisticsByWeek(routeId, busCompanyName);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(response)
                        .build()
                , HttpStatus.OK);
    }


    @GetMapping("/new-customers")
    @Operation(summary = "statistics new customer", description = "statistics new customer")
    public ResponseEntity<?> StatisticsNewCustomer() {
        StatisticsNewCustomerResponse newCustomerStatistics = statisticsService.getNewCustomerStatistics();
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(newCustomerStatistics)
                        .build(),HttpStatus.OK
        );
    }

    @GetMapping("/loyal-customers")
    @Operation(summary = "statistics loyal customer", description = "statistics loyal customer")
    public ResponseEntity<?> StatisticsLoyalCustomer() {
        StatisticsLoyalCustomerResponse response = statisticsService.getLoyalCustomerStatistics();
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(response)
                        .build(),HttpStatus.OK
        );
    }
}
