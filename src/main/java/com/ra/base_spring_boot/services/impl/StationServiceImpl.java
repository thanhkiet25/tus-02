package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.StationCreateRequest;
import com.ra.base_spring_boot.dto.req.StationUpdateRequest;
import com.ra.base_spring_boot.dto.resp.PopularStationResponse;
import com.ra.base_spring_boot.dto.resp.StationListResponse;
import com.ra.base_spring_boot.dto.resp.StationResponse;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.Station;
import com.ra.base_spring_boot.repository.IStationRepository;
import com.ra.base_spring_boot.services.IStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements IStationService {
    private final IStationRepository stationRepository;
    private final CloudinaryServiceImpl cloudinaryService;

    @Override
    public Page<StationListResponse> findAllAndSearch(Pageable pageable, String search) {
        Page<Station> listStation = stationRepository.findAllAndSearch(search, pageable);
        return listStation.map(e -> StationListResponse.builder()
                .id(e.getId())
                .stationName(e.getName())
                .location(e.getLocation())
                .image(e.getImage())
                .build());


    }

    @Override
    public StationResponse findStationById(int id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Station not found with id: " + id));
        return new StationResponse(station);

    }

    @Override
    public List<PopularStationResponse> getPopularStations(int limit) {
        return stationRepository.getPopularStations(PageRequest.of(0, limit));
    }

    @Override
    public StationResponse createStation(StationCreateRequest request) {
        if (stationRepository.existsByName(request.getName())) {
            throw new HttpConflict("Station name '" + request.getName() + "' already exists");
        }

//        if (request.getLocation() == null || request.getLocation().trim().isEmpty()) {
//            throw new HttpBadRequest("Location cannot be empty");
//        }
        Station station = new Station();
        station.setName(request.getName());
        station.setLocation(request.getLocation());
        station.setDescriptions(request.getDescriptions());

        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            String imageUrl = cloudinaryService.upload(request.getImageFile());
            station.setImage(imageUrl);
        }

        if (request.getWallpaperFile() != null && !request.getWallpaperFile().isEmpty()) {
            String wallpaperUrl = cloudinaryService.upload(request.getWallpaperFile());
            station.setWallpaper(wallpaperUrl);
        }

        Station savedStation = stationRepository.save(station);
        return new StationResponse(savedStation);
    }

    @Override
    public StationResponse updateStation(Integer id, StationUpdateRequest request) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Station not found with id: " + id));

        if (request.getName() != null && !request.getName().isEmpty()) {
            if (!station.getName().equals(request.getName())) {

                if (stationRepository.existsByNameAndIdNot(request.getName(), id)) {
                    throw new HttpConflict("Station name '" + request.getName() + "' already exists");
                }
            }
            station.setName(request.getName());
        }

        if (request.getLocation() != null && !request.getLocation().isEmpty()) {
            if (request.getLocation().trim().isEmpty()) {
                throw new HttpBadRequest("Location cannot be empty");
            }
            station.setLocation(request.getLocation());
        }


        if (request.getDescriptions() != null) {
            station.setDescriptions(request.getDescriptions());
        }

        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            String imageUrl = cloudinaryService.upload(request.getImageFile());
            station.setImage(imageUrl);
        }

        if (request.getWallpaperFile() != null && !request.getWallpaperFile().isEmpty()) {
            String wallpaperUrl = cloudinaryService.upload(request.getWallpaperFile());
            station.setWallpaper(wallpaperUrl);
        }

        Station updatedStation = stationRepository.save(station);
        return new StationResponse(updatedStation);
    }

    @Override
    public StationResponse deleteStation(Integer id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Station not found with id: " + id));

        if (stationRepository.hasRelatedRoutes(id)) {
            throw new HttpConflict("Cannot delete station because it is used in existing routes. Please remove related routes first.");
        }

        StationResponse response = new StationResponse(station);

        stationRepository.delete(station);
        return response;
    }

}

