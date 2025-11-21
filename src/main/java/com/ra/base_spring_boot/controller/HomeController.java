package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.resp.*;
import com.ra.base_spring_boot.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home")
public class HomeController {
    private final IRouteService routeService;
    private final IBannerService bannerService;
    private final IBusCompanyService busCompanyService;
    private final IBusReviewService busReviewService;
    private final IStationService stationService;
    private final IScheduleService scheduleService;
    @GetMapping("/popularRouter")
    public ResponseEntity<?> getPopularRoutes(@RequestParam(defaultValue = "5") int limit) {
        List<PopularRouteResponse> routes = routeService.getPopularRoutes(limit);
        return  ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(routes)
                .build());
    }

    @GetMapping("/banners")
    public ResponseEntity<?> getBanners() {
        List<BannerResponse> banners = bannerService.findAllBanner();
        return  ResponseEntity.ok().body(
                ResponseWrapper.builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(banners)
                .build()
        );
    }
    @GetMapping("/popularBusCompany")
    public ResponseEntity<?> getPopularBusCompany(@RequestParam(defaultValue = "5") int limit) {
        List<PopularBusCompanyResponse> popularBusCompany = busCompanyService.getPopularBusCompanyDTO(limit);
        return  ResponseEntity.ok().body(
                ResponseWrapper.builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(popularBusCompany)
                .build()
        );
    }
    @GetMapping("/popularBusReview")
    public ResponseEntity<?> getPopularBusReview(@RequestParam(defaultValue = "5") int limit) {
        List<PopularBusReviewResponse> popularBusReview = busReviewService.getBusReviews(limit);
        return  ResponseEntity.ok().body(
                ResponseWrapper.builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(popularBusReview)
                .build()
        );
    }

    @GetMapping("/popularStation")
    public ResponseEntity<?> getPopularStation(@RequestParam(defaultValue = "5") int limit) {
        List<PopularStationResponse> popularStation = stationService.getPopularStations(limit);
        return  ResponseEntity.ok().body(
                ResponseWrapper.builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(popularStation)
                .build()
        );
    }

    @GetMapping("/searchSchedule")
    public ResponseEntity<?> getSearchSchedule
            (@RequestParam(required = false ) Long fromId,
             @RequestParam(required = false ) Long toId,
             @RequestParam(required = false)
             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
             LocalDate date ){
                List<ScheduleResponse> searchSchedule = scheduleService.searchSchedule(fromId, toId, date);
                return  ResponseEntity.ok().body(
                        ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(searchSchedule)
                        .build()
                );
    }

}
