package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.resp.BusCompanyListResponse;
import com.ra.base_spring_boot.dto.resp.BusCompanyResponse;
import com.ra.base_spring_boot.dto.resp.PageResponse;
import com.ra.base_spring_boot.services.impl.BusCompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ResponseWrapper<?>> getCompanies(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<BusCompanyResponse> result = busCompanyService.getCompanyNames(pageable);
        ResponseWrapper<?> response = ResponseWrapper
                .builder()
                .status(HttpStatus.OK)
                .code(200)
                .data(result)
                .build();
        return ResponseEntity.ok(response);

    }
    @GetMapping("/companies/{id}")
    public ResponseEntity<ResponseWrapper<?>> getCompanyById(@PathVariable int id){
        try{
            BusCompanyResponse response = busCompanyService.getCompanyById(id);
            return ResponseEntity.ok(
                    ResponseWrapper.builder()
                            .status(HttpStatus.OK)
                            .code(200)
                            .data(response)
                            .build()
            );

        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseWrapper.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .code(400)
                            .data(e.getMessage())
                            .build()
                    );
        }
    }

}
