package com.ra.base_spring_boot.controller;
import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.resp.RouteListResponse;
import com.ra.base_spring_boot.dto.resp.RouteResponse;
import com.ra.base_spring_boot.services.IRouteService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/routes")
public class RouteController {

    private final IRouteService routeService;

    @GetMapping
    @Operation(summary = "get all route", description = "get all route and search by departure station and arrival station")
    public ResponseEntity<?> getAllRoute(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(defaultValue = "") String searchStation) {
        Page<RouteListResponse> listRouteResp = routeService.getAllRouteAndSearchStation(pageable, searchStation);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(listRouteResp)
                        .build(),
                HttpStatus.OK
        );
    }
    @GetMapping("/{id}")
    @Operation(summary = "get route by id", description = "get route by id")
    public ResponseEntity<?> getRouteById(@PathVariable Integer id) {
        RouteResponse route = routeService.findById(id);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(route)
                        .build(),HttpStatus.OK
        );
    }

}
