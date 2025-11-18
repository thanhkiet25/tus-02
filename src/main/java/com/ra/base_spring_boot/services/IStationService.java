package com.ra.base_spring_boot.services;


import com.ra.base_spring_boot.dto.resp.StationDataResp;
import com.ra.base_spring_boot.dto.resp.StationRespDto;
import com.ra.base_spring_boot.model.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ra.base_spring_boot.dto.resp.PopularStationDTO;

import java.util.List;

public interface IStationService {
    Page<StationDataResp> findAllAndSearch(Pageable pageable , String search);

    StationRespDto findStationById(int id);

    List<PopularStationDTO> getPopularStations(int limit);
}




