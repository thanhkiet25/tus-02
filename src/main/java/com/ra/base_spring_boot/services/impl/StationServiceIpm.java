package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.resp.PopularStationDTO;
import com.ra.base_spring_boot.dto.resp.StationDataResp;
import com.ra.base_spring_boot.dto.resp.StationRespDto;
import com.ra.base_spring_boot.model.Station;
import com.ra.base_spring_boot.repository.IStationRepository;
import com.ra.base_spring_boot.services.IStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceIpm implements IStationService {
    @Autowired
    private  IStationRepository iStationRepository;

    @Override
    public Page<StationDataResp> findAllAndSearch(Pageable pageable, String search) {
        Page<Station> listStation =iStationRepository.findAllAndSearch(pageable, search);
        return listStation.map(e -> StationDataResp.builder()
                .id(e.getId())
                .StationName(e.getName())
                .build());


    }

    @Override
    public StationRespDto findStationById(int id) {
        Station station = iStationRepository.findStationById(id);
        return new StationRespDto(station);

    }

    @Override
    public List<PopularStationDTO> getPopularStations(int limit) {
        return iStationRepository.getPopularStations(PageRequest.of(0, limit));
    }

}