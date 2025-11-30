package com.ra.base_spring_boot.services.impl;
import com.ra.base_spring_boot.dto.resp.PopularRoutesResponse;
import com.ra.base_spring_boot.dto.resp.RouteListResponse;
import com.ra.base_spring_boot.dto.resp.RouteResponse;


import com.ra.base_spring_boot.dto.req.RouteRequest;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.exception.HttpNotFound;

import com.ra.base_spring_boot.model.Route;
import com.ra.base_spring_boot.model.Station;
import com.ra.base_spring_boot.repository.IRouteRepository;
import com.ra.base_spring_boot.repository.IStationRepository;
import com.ra.base_spring_boot.services.IRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements IRouteService {
    private final IRouteRepository routeRepository;
     private final IStationRepository stationRepository;
    @Override
    public Page<RouteListResponse> getAllRoute(Pageable pageable) {
        return routeRepository.findAll(pageable).map(RouteListResponse::new);
    }
    @Override
    public RouteResponse findById(int id) {
        Route route = routeRepository.findById(id).orElseThrow(()-> new HttpNotFound("Route not found with id: " + id));
        return new RouteResponse(route);
    }

    @Override
    public List<PopularRoutesResponse> getPopularRoutes(int limit) {
        return routeRepository.getPopularRouter(PageRequest.of(0, limit));
    }

   @Override
    public Page<RouteResponse> findAllAndSearchRoute(String keyword, int page, int size, String sortBy, String direction) {
       if (keyword == null || keyword.trim().isEmpty()) {
           keyword = null;
       }else {
           keyword = keyword.trim().toLowerCase();
       }
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
       Page<Route> routes = routeRepository.findAllAndSearchRoute(keyword, pageable);
       return routes.map(r -> new RouteResponse(
               r.getId(),
               r.getPrice(),
               r.getDuration(),
               r.getDistance(),
               r.getDepartureStation().getName(),
               r.getArrivalStation().getName(),
               r.getCreatedAt(),
               r.getUpdatedAt()
       ));
    }
    // create route
    @Override
    public RouteResponse createRoute(RouteRequest routeReq) {
        //KT route ton tai
        Optional<Route> duplicateRoute = routeRepository.findDuplicateRoute(
                routeReq.getDepartureStationId(),
                routeReq.getArrivalStationId(),
                null
        );
        if(duplicateRoute.isPresent()){
            throw new HttpConflict("Route already exists");
        }

        Route route = new Route();
        route.setPrice(routeReq.getPrice());
        route.setDuration(routeReq.getDuration());
        route.setDistance(routeReq.getDistance());
// xu ly khoa ngoai
        Station departure = stationRepository.findById(routeReq.getDepartureStationId())
                .orElseThrow(() -> new HttpNotFound("Departure station not found"));
        route.setDepartureStation(departure);

        Station arrival = stationRepository.findById(routeReq.getArrivalStationId())
                .orElseThrow(() -> new HttpNotFound("Arrival station not found"));
        route.setArrivalStation(arrival);
        Route saved = routeRepository.save(route);
        return new RouteResponse(saved);
    }
   // update route
   @Override
   public RouteResponse updateRoute(int id, RouteRequest req) {
       Route route = routeRepository.findById(id)
               .orElseThrow(() -> new HttpNotFound("Route not found with id: " + id));
       //update gia tri
       route.setPrice(req.getPrice());
       route.setDuration(req.getDuration());
       route.setDistance(req.getDistance());

       // Update khóa ngoại
       Station departure = stationRepository.findById(req.getDepartureStationId())
               .orElseThrow(() -> new HttpNotFound("Departure station not found"));

       Station arrival = stationRepository.findById(req.getArrivalStationId())
               .orElseThrow(() -> new HttpNotFound("Arrival station not found"));

       route.setDepartureStation(departure);
       route.setArrivalStation(arrival);

       Route saved = routeRepository.save(route);

       return new RouteResponse(saved);
   }

    @Override
public void deleteRoute(int id) {
    Route route = routeRepository.findById(id)
            .orElseThrow(() -> new HttpNotFound("Route not found with id: " + id));
    if (routeRepository.existsScheduleByRouteId(id)){
        throw new HttpConflict("Cannot delete the route because it is associated with existing schedules.");
    }
    routeRepository.delete(route);
}

    public RouteResponse getRouteById (int id){
       Route route = routeRepository.findById(id).orElseThrow(()-> new HttpNotFound("Route not found with id: " + id));
        return RouteResponse.builder()
                .id(route.getId())
                .price(route.getPrice())
                .duration(route.getDuration())
                .distance(route.getDistance())
                .departureStation(route.getDepartureStation().getName())
                .arrivalStation(route.getArrivalStation().getName())
                .createdAt(route.getCreatedAt())
                .updatedAt(route.getUpdatedAt())
                .build();
   }

}
