package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.services.IPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Tag(name = "Payment Management", description = "APIs for handling VNPay transactions")
public class PaymentController {

    private final IPaymentService paymentService;

    @GetMapping("/create-vnpay")
    @Operation(
            summary = "Create VNPay Payment URL",
            description = "Generates a secure payment URL to redirect user to VNPay gateway. Checks for ticket validity before generating."
    )
    public ResponseEntity<?> createPayment(
            @RequestParam long amount,
            @RequestParam(defaultValue = "0") Integer paymentId,
            HttpServletRequest request) {

        String vnpayUrl = paymentService.createVnPayUrl(paymentId, amount, request);

        return ResponseEntity.ok(ResponseWrapper.builder()
                .status(HttpStatus.OK)
                .code(200)
                .data(vnpayUrl)
                .build());
    }

    @GetMapping("/vnpay-return")
    @Operation(
            summary = "Handle VNPay Return Callback",
            description = "This endpoint is called by VNPay after transaction processing. It verifies the checksum and updates payment status."
    )
    public ResponseEntity<?> vnpayReturn(HttpServletRequest request) {
        int paymentStatus = paymentService.orderReturn(request);

        String resultInfo = (paymentStatus == 1) ? "Payment Success" : "Payment Failed";

        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(resultInfo)
                        .build()
        );
    }
}