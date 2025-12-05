package com.ra.base_spring_boot.controller;
import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.resp.*;
import com.ra.base_spring_boot.services.*;
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

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/statistics")
public class AdminStatisticsController {
    private final IBusCompanyService busCompanyService;
    private final ITicketService ticketService;
    private final IPaymentService paymentService;
    private final IStationService stationService;
    private final IStatisticsService statisticsService;
    private final IExportExcelService iExportExcelService;

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

    @GetMapping("/revenue-by-date")
    @Operation(summary = "revenue by date", description = "statistics revenue by date with route id and bus company name")
    public ResponseEntity<?> revenueStatisticsByCurrentWeek(
            @RequestParam Integer routeId,
            @RequestParam String busCompanyName,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
            ) {
        RevenueStatisticsResponse response = statisticsService.getRevenueStatisticsForDate(routeId, busCompanyName, startDate, endDate);
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

    // file excel
    @GetMapping("/revenue-by-date/excel")
    public ResponseEntity<byte[]> exportRevenueByDateExcel(
            @RequestParam Integer routeId,
            @RequestParam String busCompanyName,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) throws IOException {
        RevenueStatisticsResponse response =
                statisticsService.getRevenueStatisticsForDate(routeId, busCompanyName, startDate, endDate);

        String[] headers = {
                "Route ID",
                "Bus company",
                "Seat type",
                "Price",
                "Payment date",
                "Payment method"
        };
        List<Object[]> rows = new ArrayList<>();

        for (RevenueDetailResponse d : response.getDetails()) {
            rows.add(new Object[]{
                    d.getRouteId(),
                    d.getBusCompanyName(),
                    d.getSeatType(),
                    d.getAmount(),
                    d.getPaymentDate(),
                    d.getPaymentMethod()
            });
        }
        byte[] excel = iExportExcelService.exportExcel("RevenueByDate", headers, rows);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=revenue_by_date.xlsx")
                .body(excel);
    }

    @GetMapping("/revenue-by-month/excel")
    public ResponseEntity<byte[]> exportRevenueByMonthExcel(
            @RequestParam Integer routeId,
            @RequestParam String busCompanyName,
            @RequestParam Integer month,
            @RequestParam Integer year
    ) throws IOException {

        RevenueStatisticsResponse response =
                statisticsService.getRevenueStatisticsByRouteAndBusCompanyForMonth(routeId, busCompanyName, month, year);

        String[] headers = {
                "Route ID",
                "Bus company",
                "Seat type",
                "Price",
                "Payment date",
                "Payment method"
        };

        List<Object[]> rows = new ArrayList<>();

        for (RevenueDetailResponse d : response.getDetails()) {
            rows.add(new Object[]{
                    d.getRouteId(),
                    d.getBusCompanyName(),
                    d.getSeatType(),
                    d.getAmount(),
                    d.getPaymentDate(),
                    d.getPaymentMethod()
            });
        }
        byte[] excel = iExportExcelService.exportExcel("RevenueByMonth", headers, rows);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=revenue_by_month.xlsx")
                .body(excel);
    }

    @GetMapping("/revenue-by-year/excel")
    public ResponseEntity<byte[]> exportRevenueByYearExcel(
            @RequestParam Integer routeId,
            @RequestParam String busCompanyName,
            @RequestParam Integer year
    ) throws IOException {

        RevenueStatisticsResponse response =
                statisticsService.getRevenueStatisticsByRouteAndBusCompanyForYear(routeId, busCompanyName, year);

        String[] headers = {
                "Route ID",
                "Bus company",
                "Seat type",
                "Price",
                "Payment date",
                "Payment method"
        };
        List<Object[]> rows = new ArrayList<>();

        for (RevenueDetailResponse d : response.getDetails()) {
            rows.add(new Object[]{
                    d.getRouteId(),
                    d.getBusCompanyName(),
                    d.getSeatType(),
                    d.getAmount(),
                    d.getPaymentDate(),
                    d.getPaymentMethod()
            });
        }
        byte[] excel = iExportExcelService.exportExcel("RevenueByYear", headers, rows);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=revenue_by_year.xlsx")
                .body(excel);
    }

    @GetMapping("/new-customers/excel")
    public ResponseEntity<byte[]> exportNewCustomersExcel() throws IOException {

        StatisticsNewCustomerResponse response = statisticsService.getNewCustomerStatistics();

        String[] headers = {"Email khách hàng", "Tổng số vé đã đặt"};

        List<Object[]> rows = new ArrayList<>();
        for (StatisticsNewCustomerResponse.CustomerInfoDTO c : response.getNewCustomers()) {
            rows.add(new Object[]{
                    c.getEmail(),
                    c.getTotalTickets()
            });
        }
        byte[] excel = iExportExcelService.exportExcel("NewCustomers", headers, rows);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=new_customers.xlsx")
                .body(excel);
    }

    @GetMapping("/loyal-customers/excel")
    public ResponseEntity<byte[]> exportLoyalCustomersExcel() throws IOException {

        StatisticsLoyalCustomerResponse response = statisticsService.getLoyalCustomerStatistics();

        String[] headers = {"Email khách hàng", "Tổng số vé đã đặt"};

        List<Object[]> rows = new ArrayList<>();
        for (StatisticsLoyalCustomerResponse.CustomerInfoDTO c : response.getLoyalCustomers()) {
            rows.add(new Object[]{
                    c.getEmail(),
                    c.getTotalTickets()
            });
        }
        byte[] excel = iExportExcelService.exportExcel("LoyalCustomers", headers, rows);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=loyal_customers.xlsx")
                .body(excel);
    }

    @GetMapping("/statisticBusCompany/excel")
    public ResponseEntity<byte[]> exportStatisticBusCompanyExcel(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws IOException {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<StatisticBusCompany> statisticPage = busCompanyService.getStatisticBusCompany(pageable);

        String[] headers = {"ID Nhà xe", "Tên nhà xe", "Tổng chuyến", "Tổng doanh thu"};

        List<Object[]> rows = statisticPage.getContent().stream()
                .map(item -> new Object[]{
                        item.getCompanyId(),
                        item.getCompanyName(),
                        item.getPositive(),
                        item.getNegative(),
                })
                .toList();

        byte[] excel = iExportExcelService.exportExcel("StatisticBusCompany", headers, rows);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=statistic_bus_company.xlsx")
                .body(excel);
    }

    @GetMapping("/statisticTicketActive/excel")
    public ResponseEntity<byte[]> exportStatisticTicketActiveExcel() throws IOException {

        StatisticTicketActiveResponse data = ticketService.getTicketActive();

        String[] headers = {"Vé đang hoạt động", "Vé đã hủy"};

        List<Object[]> rows = new java.util.ArrayList<>();
        rows.add(new Object[]{
                data.getBooked(),
                data.getCancelled()
        });
        byte[] excel = iExportExcelService.exportExcel("TicketActive", headers, rows);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=ticket_active_statistics.xlsx")
                .body(excel);
    }


    @GetMapping("/statisticPaymentProvider/excel")
    public ResponseEntity<byte[]> exportStatisticPaymentProviderExcel() throws IOException {
        List<StatisticPaymentProviderResponse> list = paymentService.statisticByPaymentProvider();
        String[] headers = {"Phương thức thanh toán", "Số lượng giao dịch", "Tổng doanh thu"};

        // 2. Build rows -> luôn dùng ArrayList để tránh lỗi type inference
        List<Object[]> rows = new java.util.ArrayList<>();
        for (StatisticPaymentProviderResponse item : list) {
            rows.add(new Object[]{
                    item.getProviderName(),
                    item.getTotalTransactions(),
                    item.getTotalAmount(),
            });
        }
        byte[] excel = iExportExcelService.exportExcel("PaymentProviderStats", headers, rows);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=payment_provider_statistics.xlsx")
                .body(excel);
    }
    @GetMapping("/statisticStation/excel")
    public ResponseEntity<byte[]> exportStatisticStationExcel() throws IOException {

        List<StatisticStationResponse> list = stationService.getStatisticStations();

        String[] headers = {"Tên bến xe", "Lượt xe xuất phát", "Lượt xe đến"};

        List<Object[]> rows = new java.util.ArrayList<>();

        for (StatisticStationResponse item : list) {
            rows.add(new Object[]{
                    item.getStationName(),
                    item.getTotalPassengers()
            });
        }

        byte[] excel = iExportExcelService.exportExcel("StationStatistic", headers, rows);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=station_statistics.xlsx")
                .body(excel);
    }



}
