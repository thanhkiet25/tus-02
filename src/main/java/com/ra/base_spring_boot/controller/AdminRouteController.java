package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.RouteRequest;
import com.ra.base_spring_boot.dto.resp.RouteResponse;
import com.ra.base_spring_boot.services.impl.RouteServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/routes")
public class AdminRouteController {
    private final RouteServiceImpl routeService;
    @GetMapping
    public ResponseEntity<?> getAllRoutes(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Page<RouteResponse> routePage = routeService.findAllAndSearchRoute(keyword, page, size, sortBy, direction);
        return new ResponseEntity<>(ResponseWrapper
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(routePage)
                .build(),
                HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<?> createRoute(@Valid @RequestBody RouteRequest req) {
        return new ResponseEntity<>(ResponseWrapper
                .builder()
                .status(HttpStatus.CREATED)
                .code(HttpStatus.CREATED.value())
                .data(routeService.createRoute(req))
                .build(),
                HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoute( @PathVariable int id, @Valid @RequestBody RouteRequest req) {
        return new  ResponseEntity<>(ResponseWrapper
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(routeService.updateRoute(id, req))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoute( @PathVariable int id) {
        routeService.deleteRoute(id);
        return new ResponseEntity<>(ResponseWrapper
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data("Deleted")
                .build(),
                HttpStatus.OK);
    }

}
