package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.PaymentProviderRequest;
import com.ra.base_spring_boot.dto.resp.PaymentProviderListResponse;
import com.ra.base_spring_boot.dto.resp.PaymentProviderResponse;
import com.ra.base_spring_boot.model.PaymentProvider;
import com.ra.base_spring_boot.services.IPaymentProviderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/payment-providers")
public class AdminPaymentProviderController {
    private final IPaymentProviderService paymentProviderService;

    @GetMapping
    @Operation(summary = "get all payment provider", description = "get all payment provider")
    public ResponseEntity<?> getAllPaymentProviders(@PageableDefault(page = 0, size = 10, sort = "providerName", direction = Sort.Direction.ASC) Pageable pageable,
                                                    @RequestParam(defaultValue = "") String searchProviderName) {
        Page<PaymentProviderListResponse> providerListResponses = paymentProviderService.findAllByPaymentProviderAndSearchByProviderName(searchProviderName, pageable);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(providerListResponses)
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create payment provider", description = "Create a new payment provider")
    public ResponseEntity<?> createPaymentProvider(@Valid @RequestBody PaymentProviderRequest paymentProviderRequest) {
        PaymentProviderResponse paymentProviderResponse = paymentProviderService.createPaymentProvider(paymentProviderRequest);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(paymentProviderResponse)
                        .build()
                , HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete payment provider", description = "Delete a payment provider by ID")
    public ResponseEntity<?> deletePaymentProvider(@PathVariable int id) {
        paymentProviderService.deletePaymentProvider(id);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("Delete paymentProvider successfully!")
                        .build(),
                HttpStatus.OK);
    }
}
