package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.resp.BusCompanyListResponse;
import com.ra.base_spring_boot.dto.resp.BusCompanyResponse;
import com.ra.base_spring_boot.dto.resp.PageResponse;
import com.ra.base_spring_boot.services.impl.BusCompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class BusCompanyController {
    @Autowired
    private BusCompanyServiceImpl busCompanyService;

    @GetMapping("/companies")
    public ResponseEntity<?> getCompanies(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<BusCompanyListResponse> result = busCompanyService.getCompanyNames(pageable);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(result)
                        .build(),
                HttpStatus.OK
        );
    }
    @GetMapping("/companies/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable int id){
        BusCompanyResponse response = busCompanyService.getCompanyById(id);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(response)
                        .build(),
                HttpStatus.OK
        );
    }

}
