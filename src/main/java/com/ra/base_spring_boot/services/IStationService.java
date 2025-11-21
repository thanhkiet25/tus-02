package com.ra.base_spring_boot.services;


import com.ra.base_spring_boot.dto.resp.StationListResponse;
import com.ra.base_spring_boot.dto.resp.StationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ra.base_spring_boot.dto.resp.PopularStationResponse;

import java.util.List;

public interface IStationService {
    Page<StationListResponse> findAllAndSearch(Pageable pageable , String search);

    StationResponse findStationById(int id);

    List<PopularStationResponse> getPopularStations(int limit);
}




