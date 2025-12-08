package com.ra.base_spring_boot.services;
import com.ra.base_spring_boot.dto.resp.PopularRoutesResponse;
import com.ra.base_spring_boot.dto.resp.RouteListResponse;
import com.ra.base_spring_boot.dto.resp.RouteResponse;


import com.ra.base_spring_boot.dto.req.RouteCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRouteService {


 Page<RouteListResponse> getAllRouteAndSearchStation(Pageable pageable, String searchStation);


 RouteResponse findById(int id);

 List<PopularRoutesResponse> getPopularRoutes(int limit);

 // Hien thi danh sach cac route
 Page<RouteResponse> findAllAndSearchRoute( String keyword, int page, int size, String sortBy, String direction);
 // Them moi route
 RouteResponse createRoute(RouteCreateRequest req);

 // Cập nhật route
 RouteResponse updateRoute(int id, RouteCreateRequest req);

 // Xóa route
 void deleteRoute(int id);
}
