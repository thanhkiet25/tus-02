package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.RouteListResponse;
import com.ra.base_spring_boot.dto.resp.PopularRouteResponse;
import com.ra.base_spring_boot.dto.resp.RouteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRouteService {
 Page<RouteListResponse> getAllRoute(Pageable pageable);

 RouteResponse findById(int id);

 List<PopularRouteResponse> getPopularRoutes(int limit);

}
