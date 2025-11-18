package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.resp.BannerDTO;
import com.ra.base_spring_boot.dto.resp.ListRouteResp;
import com.ra.base_spring_boot.dto.resp.PopularRoutesDTO;
import com.ra.base_spring_boot.dto.resp.RouteRespDto;
import com.ra.base_spring_boot.model.Banner;
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
    public Page<ListRouteResp> getAllRoute(Pageable pageable) {
        return routeRepository.findAll(pageable).map(ListRouteResp::new);
    }

    @Override
    public RouteRespDto findById(int id) {
        Route route = routeRepository.findById(id);
        return new RouteRespDto(route);
    }

    @Override
    public List<PopularRoutesDTO> getPopularRoutes(int limit) {
        return routeRepository.getPopularRouter(PageRequest.of(0, limit));
    }




}
