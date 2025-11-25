package com.ra.base_spring_boot.controller;
import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.services.IStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stations")
public class StationController {

    private final IStationService stationService;


    @GetMapping
    public ResponseEntity<?> findAllAndSearch(
            @RequestParam(defaultValue = "") String search,
            @PageableDefault(page = 0, size = 10 , sort = "createdAt" ,direction = Sort.Direction.DESC)Pageable pageable){
        return new ResponseEntity<>(ResponseWrapper.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(stationService.findAllAndSearch(pageable, search))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findStationById(@PathVariable int id){
        return new ResponseEntity<>(ResponseWrapper.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(stationService.findStationById(id))
                .build(),HttpStatus.OK);
    }
}