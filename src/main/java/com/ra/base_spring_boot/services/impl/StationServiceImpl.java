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

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements IStationService {
    private final IStationRepository stationRepository;
    private final CloudinaryServiceImpl cloudinaryService;

    @Override
    public Page<StationListResponse> findAllAndSearch(Pageable pageable, String search) {
        if (search != null) {
            search = search.trim();
        }

        Page<Station> listStation =stationRepository.findAllAndSearch(search, pageable);
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

        if (stationRepository.existsByName(request.getName().trim())) {
            throw new HttpConflict("Station name '" + request.getName().trim() + "' already exists");
        }

        Station station = new Station();
        station.setName(request.getName().trim());
        station.setLocation(request.getLocation().trim());

        if (request.getDescriptions() != null) {
            station.setDescriptions(request.getDescriptions().trim());
        }

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

        boolean isModified = false;

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            String newName = request.getName().trim();
            if (!station.getName().equals(newName)) {
                if (stationRepository.existsByNameAndIdNot(newName, id)) {
                    throw new HttpConflict("Station name '" + newName + "' already exists");
                }
                station.setName(newName);
                isModified = true;
            }

        }

        if (request.getLocation() != null && !request.getLocation().trim().isEmpty()) {
            String newLocation = request.getLocation().trim();
            if (!station.getLocation().equals(newLocation)) {
                station.setLocation(newLocation);
                isModified = true;
            }
        }

        if (request.getDescriptions() != null) {
            String newDesc = request.getDescriptions().trim();
            String oldDesc = station.getDescriptions() != null ? station.getDescriptions() : "";

            if (!oldDesc.equals(newDesc)) {
                station.setDescriptions(newDesc);
                isModified = true;
            }
        }

        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            String imageUrl = cloudinaryService.upload(request.getImageFile());
            station.setImage(imageUrl);
            isModified = true;
        }

        if (request.getWallpaperFile() != null && !request.getWallpaperFile().isEmpty()) {
            String wallpaperUrl = cloudinaryService.upload(request.getWallpaperFile());
            station.setWallpaper(wallpaperUrl);
            isModified = true;
        }

        if (!isModified) {
            throw new HttpBadRequest("No changes detected.");
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

