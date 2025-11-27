package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.CancellationPolicyRequest;
import com.ra.base_spring_boot.dto.resp.CancellationPolicyListResponse;
import com.ra.base_spring_boot.dto.resp.CancellationPolicyResponse;
import com.ra.base_spring_boot.model.CancellationPolicy;
import com.ra.base_spring_boot.services.ICancellationPolicyService;
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
@RequestMapping("/api/v1/admin/cancellation-policy")
public class AdminCancellationPolicyController {

    private final ICancellationPolicyService cancellationPolicyService;

    @GetMapping
    @Operation(summary = "get all cancellation policy", description = "get all cancellation policy by search departureStation or arrivalStation")
    public ResponseEntity<?> getAllCancellationPolicies(@PageableDefault(page = 0, size = 10)Pageable  pageable,
                                                        @RequestParam (defaultValue = "") String search) {
        Page<CancellationPolicyListResponse> listCancellation = cancellationPolicyService.getAllCancellationPolicyAndSearchByRoute(search, pageable);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(listCancellation.getContent())
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get cancellation policy by Id", description = "get cancellation policy by Id")
    public ResponseEntity<?> getCancellationPolicy(@PathVariable Integer id) {
        CancellationPolicyResponse cancellationPolicyResponse = cancellationPolicyService.findById(id);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(cancellationPolicyResponse)
                        .build(),HttpStatus.OK
        );
    }

    @PostMapping
    @Operation(summary = "create cancellation", description = "create new cancellation policy")
    public ResponseEntity<?> createCancellationPolicy(@Valid @RequestBody CancellationPolicyRequest cancellationPolicyRequest) {
        CancellationPolicyResponse newCancellation =  cancellationPolicyService.createCancellationPolicy(cancellationPolicyRequest);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(newCancellation)
                        .build(),HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete cancellation policy", description = "delete cancellation policy by Id")
    public ResponseEntity<?> deleteCancellationPolicy(@PathVariable Integer id) {
        cancellationPolicyService.deleteCancellationPolicy(id);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("Delete cancellation policy successfully")
                        .build(),HttpStatus.OK
        );
    }
}
