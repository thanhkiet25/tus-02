package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.resp.RouteListResponse;
import com.ra.base_spring_boot.dto.resp.PopularRouteResponse;
import com.ra.base_spring_boot.dto.resp.RouteResponse;
import com.ra.base_spring_boot.model.Route;
import com.ra.base_spring_boot.repository.IRouteRepository;
import com.ra.base_spring_boot.services.IRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements IRouteService {
    private final IRouteRepository routeRepository;

    @Override
    public Page<RouteListResponse> getAllRoute(Pageable pageable) {
        return routeRepository.findAll(pageable).map(RouteListResponse::new);
    }

    @Override
    public RouteResponse findById(int id) {
        Route route = routeRepository.findById(id);
        return new RouteResponse(route);
    }

    @Override
    public List<PopularRouteResponse> getPopularRoutes(int limit) {
        return routeRepository.getPopularRouter(PageRequest.of(0, limit));
    }




}
