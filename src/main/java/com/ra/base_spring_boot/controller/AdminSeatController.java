package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.SeatRequest;
import com.ra.base_spring_boot.dto.resp.SeatListResponse;
import com.ra.base_spring_boot.dto.resp.SeatResponse;
import com.ra.base_spring_boot.model.BusCompany;
import com.ra.base_spring_boot.model.Seat;
import com.ra.base_spring_boot.services.IBusCompanyService;
import com.ra.base_spring_boot.services.ISeatService;
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
@RequestMapping("api/v1/admin/seats")
public class AdminSeatController {

    private  final ISeatService seatService;

    private final IBusCompanyService busCompanyService;

    @GetMapping("/{bus_company_id}")
    public ResponseEntity<?> getAllSeatByBusCompany(@PathVariable int bus_company_id, @PageableDefault(page = 0, size = 10,sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam (defaultValue = "")  String searchBusName) {
       Page<SeatListResponse> seatListResponse = seatService.findAllByBusCompany(bus_company_id, searchBusName, pageable);
       return new ResponseEntity<>(
               ResponseWrapper.builder()
                       .status(HttpStatus.OK)
                       .code(HttpStatus.OK.value())
                       .data(seatListResponse.getContent())
                       .build()
              , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createSeat(@Valid @RequestBody SeatRequest seatRequest){
        SeatResponse seatResponse =  seatService.createSeat(seatRequest);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(seatResponse)
                        .build()
                , HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeat(@PathVariable int id){
        seatService.deleteSeat(id);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("Delete seat successfully")
                        .build(),HttpStatus.OK
        );
    }

    @GetMapping("/findById/{seatId}")
    @Operation(summary = "get seat by Id", description = "get seat by Id")
    public ResponseEntity<?> getSeatById(@PathVariable int seatId){
        SeatResponse seatResponse = seatService.findSeatById(seatId);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(seatResponse)
                        .build(),HttpStatus.OK
        );
    }

}
