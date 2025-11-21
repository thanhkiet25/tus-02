package com.ra.base_spring_boot.controller;
import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.resp.ListRouteResponse;
import com.ra.base_spring_boot.dto.resp.RouteResponse;
import com.ra.base_spring_boot.services.IRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/routes")
public class RouteController {

    private final IRouteService routeService;

    @GetMapping
    public ResponseEntity<?> getAllRoute(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<ListRouteResponse> listRouteResp = routeService.getAllRoute(pageable);
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
