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
@RequestMapping("/api/v1/bus-review")
public class BusReviewController {
    private final IBusReviewService busReviewService;

    @GetMapping
    public ResponseEntity<?> findAllBusReviews(@PageableDefault(page = 0, size = 10, sort = "rating",direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(busReviewService.findAllBusReviews(pageable))
                .build(),HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> saveBusReview(@Valid @RequestBody BusReviewCreateResponse busReviewCreateResponse) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(busReviewService.saveBusReview(busReviewCreateResponse))
                .build(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBusReview(@PathVariable int id) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(busReviewService.deleteBusReview(id))
                .build(), HttpStatus.OK);
    }
}
