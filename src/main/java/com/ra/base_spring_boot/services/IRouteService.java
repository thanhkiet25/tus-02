package com.ra.base_spring_boot.services;
import com.ra.base_spring_boot.dto.resp.ListRouteResponse;
import com.ra.base_spring_boot.dto.resp.PopularRoutesResponse;
import com.ra.base_spring_boot.dto.resp.RouteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRouteService {

 Page<ListRouteResponse> getAllRoute(Pageable pageable);

 RouteResponse findById(int id);

 List<PopularRoutesResponse> getPopularRoutes(int limit);


}
