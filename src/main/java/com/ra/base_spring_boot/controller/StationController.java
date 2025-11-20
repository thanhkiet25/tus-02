package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.services.impl.StationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/station")
public class StationController {
    @Autowired
    private StationServiceImpl stationServiceIpm;

    @GetMapping
    public ResponseEntity<?> findAllAndSearch(@RequestParam(defaultValue = "") String search,
                                              @PageableDefault(page = 0, size = 10 , sort = "createdAt" ,direction = Sort.Direction.ASC)Pageable pageable){
        return new ResponseEntity<>(ResponseWrapper.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(stationServiceIpm.findAllAndSearch(pageable, search))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findStationById(@PathVariable int id){
        return new ResponseEntity<>(ResponseWrapper.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(stationServiceIpm.findStationById(id))
                .build(),HttpStatus.OK);
    }
}