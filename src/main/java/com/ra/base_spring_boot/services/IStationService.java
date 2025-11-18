package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.StationDataResp;
import com.ra.base_spring_boot.dto.resp.StationRespDto;
import com.ra.base_spring_boot.model.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStationService {
    Page<StationDataResp> findAllAndSearch(Pageable pageable , String search);

    StationRespDto findStationById(int id);
}