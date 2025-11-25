package com.ra.base_spring_boot.services;
import com.ra.base_spring_boot.dto.req.StationCreateRequest;
import com.ra.base_spring_boot.dto.req.StationUpdateRequest;
import com.ra.base_spring_boot.dto.resp.StationListResponse;
import com.ra.base_spring_boot.dto.resp.StationResponse;

import com.ra.base_spring_boot.model.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ra.base_spring_boot.dto.resp.PopularStationResponse;

import java.util.List;
import java.util.Set;

public interface IStationService {

    Page<StationListResponse> findAllAndSearch(Pageable pageable , String search);

    StationResponse findStationById(int id);

    List<PopularStationResponse> getPopularStations(int limit);

    StationResponse createStation(StationCreateRequest request);

    StationResponse updateStation(Integer id, StationUpdateRequest request);

    StationResponse deleteStation(Integer id);

}




