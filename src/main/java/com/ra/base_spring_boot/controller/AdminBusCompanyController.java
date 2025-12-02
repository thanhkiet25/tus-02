package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.BusCompanyCreateRequest;
import com.ra.base_spring_boot.dto.req.BusCompanyUpdateRequest;
import com.ra.base_spring_boot.dto.resp.BusCompanyResponse;
import com.ra.base_spring_boot.services.IBusCompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/bus-companies")
public class AdminBusCompanyController {
    private final IBusCompanyService busCompanyService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<BusCompanyResponse>>>  findBusCompanyByNameAndGetAllAndSort(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "companyName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
            ){
       Page<BusCompanyResponse> busCompany = busCompanyService.findBusCompanyByNameAndGetAllAndSort(search,page,size,sortBy,direction);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.<Page<BusCompanyResponse>>builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(busCompany)
                        .build()
        );
    }
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseWrapper<?>>  createBusCompany(@Valid @ModelAttribute BusCompanyCreateRequest busCompanyRequest){
        BusCompanyResponse busCompany = busCompanyService.createBusCompany(busCompanyRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(201)
                        .data(busCompany)
                        .build()
        );
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findBusCompany(@PathVariable Integer id){
        BusCompanyResponse busCompany = busCompanyService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.<BusCompanyResponse>builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(busCompany)
                .build()
        );
    }
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseWrapper<?>> updateBusCompany(@PathVariable Integer id,@ModelAttribute BusCompanyUpdateRequest busCompanyRequest){
        BusCompanyResponse update = busCompanyService.updateBusCompany(id,busCompanyRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(200)
                        .status(HttpStatus.OK)
                        .data(update)
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<?>> deleteBusCompany(@PathVariable int id){
       BusCompanyResponse busCompanyResponse= busCompanyService.deleteBusCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(busCompanyResponse)
                        .build()
        );
    }
}
