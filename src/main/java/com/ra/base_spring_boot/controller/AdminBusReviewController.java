package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.BusReviewCreateResponse;
import com.ra.base_spring_boot.services.IBusReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/bus-review")
public class AdminBusReviewController {
    private final IBusReviewService busReviewService;

    @GetMapping
    public ResponseEntity<?> getBusReviews(
            @RequestParam(defaultValue = "") String search,
            @RequestParam (defaultValue = "")String filterBus,
            @PageableDefault(page = 0,size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(busReviewService.findAllSearchAndFilter(search, filterBus, pageable))
                .build(), HttpStatus.OK);
    }




}
