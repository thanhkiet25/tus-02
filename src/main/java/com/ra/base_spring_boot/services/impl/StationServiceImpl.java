package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.resp.PopularStationResponse;
import com.ra.base_spring_boot.dto.resp.StationListResponse;
import com.ra.base_spring_boot.dto.resp.StationResponse;
import com.ra.base_spring_boot.model.Station;
import com.ra.base_spring_boot.repository.IStationRepository;
import com.ra.base_spring_boot.services.IStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements IStationService {
    @Autowired
    private  IStationRepository iStationRepository;

    @Override
    public Page<StationListResponse> findAllAndSearch(Pageable pageable, String search) {
        Page<Station> listStation =iStationRepository.findAllAndSearch(pageable, search);
        return listStation.map(e -> StationListResponse.builder()
                .id(e.getId())
                .StationName(e.getName())
                .build());


    }

    @Override
    public StationResponse findStationById(int id) {
        Station station = iStationRepository.findStationById(id);
        return new StationResponse(station);

    }

    @Override
    public List<PopularStationResponse> getPopularStations(int limit) {
        return iStationRepository.getPopularStations(PageRequest.of(0, limit));
    }

}