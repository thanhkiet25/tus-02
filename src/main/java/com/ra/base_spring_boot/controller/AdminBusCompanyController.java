package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.BusCompanyRequest;
import com.ra.base_spring_boot.dto.resp.BusCompanyResponse;
import com.ra.base_spring_boot.model.BusCompany;
import com.ra.base_spring_boot.services.IBusCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
            @RequestParam(defaultValue = "desc") String direction
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
    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper<?>>  createBusCompany(@RequestBody BusCompanyRequest busCompanyRequest){
        BusCompany busCompany = busCompanyService.createBusCompany(busCompanyRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(201)
                        .data(busCompany)
                        .build()
        );
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseWrapper<?>> updateBusCompany(@PathVariable int id,@RequestBody BusCompanyRequest busCompanyRequest){
        BusCompany update = busCompanyService.updateBusCompany(id,busCompanyRequest);
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
        busCompanyService.deleteBusCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(null)
                        .build()
        );
    }
}
