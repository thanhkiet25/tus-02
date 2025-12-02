package com.ra.base_spring_boot.controller;
import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.resp.StatisticBusCompany;
import com.ra.base_spring_boot.dto.resp.StatisticPaymentProviderResponse;
import com.ra.base_spring_boot.dto.resp.StatisticStationResponse;
import com.ra.base_spring_boot.dto.resp.StatisticTicketActiveResponse;
import com.ra.base_spring_boot.services.IBusCompanyService;
import com.ra.base_spring_boot.services.IPaymentService;
import com.ra.base_spring_boot.services.IStationService;
import com.ra.base_spring_boot.services.ITicketService;
import com.ra.base_spring_boot.dto.resp.RevenueStatisticsResponse;
import com.ra.base_spring_boot.dto.resp.StatisticsLoyalCustomerResponse;
import com.ra.base_spring_boot.dto.resp.StatisticsNewCustomerResponse;
import com.ra.base_spring_boot.services.IStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/statistics")
public class StatisticsController {
    private final IBusCompanyService busCompanyService;
    private final ITicketService ticketService;
    private final IPaymentService paymentService;
    private final IStationService stationService;
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
                  ,HttpStatus.OK);
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
                        .build(), HttpStatus.OK);
    }

    @GetMapping("/statisticBusCompany")
    public ResponseEntity<?> getStatisticBusCompany(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page -1, size);
        Page<StatisticBusCompany> statisticBusCompany = busCompanyService.getStatisticBusCompany(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(statisticBusCompany)
                        .build()
        );
    }

    @GetMapping("/statisticTicketActive")
    public ResponseEntity<?> getStatisticTicketActive(){
        StatisticTicketActiveResponse statisticTicketActive = ticketService.getTicketActive();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(statisticTicketActive)
                        .build()
        );
    }

    @GetMapping("/statisticPaymentProvider")
    public ResponseEntity<?> getStatisticPaymentProvider(){
        List<StatisticPaymentProviderResponse> statisticPaymentProviderResponse = paymentService.statisticByPaymentProvider();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(statisticPaymentProviderResponse)
                        .build()
        );
    }

    @GetMapping("/statisticStation")
    public ResponseEntity<?> getStatisticStation(){
        List<StatisticStationResponse> statisticStation = stationService.getStatisticStations();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(statisticStation)
                        .build()
        );
    }
}
