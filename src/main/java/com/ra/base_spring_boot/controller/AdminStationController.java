package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.StationCreateRequest;
import com.ra.base_spring_boot.dto.req.StationUpdateRequest;
import com.ra.base_spring_boot.dto.resp.StationResponse;
import com.ra.base_spring_boot.services.IStationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/stations")
@RequiredArgsConstructor
public class AdminStationController {
    private final IStationService stationService;

    @GetMapping
    @Operation(
            summary = "Get all stations",
            description = "Retrieve a paginated list of bus stations. Supports searching by station name or location, and sorting by specific fields."
    )
    public ResponseEntity<?> getAllStations(
            @RequestParam(defaultValue = "") String search,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable)
    {
        return ResponseEntity.ok(ResponseWrapper.builder()
                .status(HttpStatus.OK)
                .code(200)
                .data(stationService.findAllAndSearch(pageable, search))
                .build());
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create new station", description = "Upload images and create a new station")
    public ResponseEntity<?> createStation(@Valid @ModelAttribute StationCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(201)
                        .data(stationService.createStation(request))
                        .build()
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update station", description = "Update station info and images")
    public ResponseEntity<?> updateStation(
            @PathVariable Integer id,
            @Valid @ModelAttribute StationUpdateRequest request) {
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(stationService.updateStation(id, request))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete station", description = "Delete a station by ID")
    public ResponseEntity<?> deleteStation(@PathVariable Integer id) {
        StationResponse deletedStation = stationService.deleteStation(id);

        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(deletedStation)
                        .build()
        );
    }
}
