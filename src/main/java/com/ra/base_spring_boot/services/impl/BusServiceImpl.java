package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.BusCreateRequest;
import com.ra.base_spring_boot.dto.req.BusUpdateRequest;
import com.ra.base_spring_boot.dto.resp.BusResponse;
import com.ra.base_spring_boot.dto.resp.BusListResponse;
import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.*;
import com.ra.base_spring_boot.repository.IBusRepository;
import com.ra.base_spring_boot.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements IBusService {
    private final IBusRepository busRepository;

    private final CloudinaryServiceImpl cloudinaryService;

    private final BusCompanyServiceImpl busCompanyService;

    private final IStationService stationService;

    @Override
    public BusResponse save(BusCreateRequest busReqDto) {
        if(busRepository.existsBusByName(busReqDto.getName())){
            throw new HttpConflict("duplicate name");
        }
        Bus bus = new Bus();
        bus.setName(busReqDto.getName());
        bus.setDescriptions(busReqDto.getDescriptions());
        bus.setLicensePlate(busReqDto.getLicensePlate());
        bus.setCapacity(busReqDto.getCapacity());
        bus.setBusCompany(busCompanyService.findBusCompanyById(busReqDto.getBusCompanyId()));
        bus.setCreatedAt(LocalDateTime.now());

        //upload anh
        if(busReqDto.getBusImage() != null && !busReqDto.getBusImage().isEmpty()){
            for (MultipartFile file : busReqDto.getBusImage()){
                String image = cloudinaryService.upload(file);
                BusImage busImage = new BusImage();
                busImage.setImageUrl(image);
                busImage.setBus(bus);
                bus.getImages().add(busImage);
            }
        }

        Bus saveBus = busRepository.save(bus);
        String listImage = saveBus.getImages().stream().map(BusImage::getImageUrl).collect(Collectors.joining(","));
        return BusResponse.builder()
                .name(saveBus.getName())
                .licensePlate(saveBus.getLicensePlate())
                .capacity(saveBus.getCapacity())
                .busCompanyName(saveBus.getBusCompany().getCompanyName())
                .busImage(listImage)
                .createdAt(LocalDateTime.now())
                .descriptions(saveBus.getDescriptions())
                .build();
    }

    @Override
    public BusResponse update(BusCreateRequest busCreateRequest, int id) {
        Bus bus = busRepository.findById(id).orElseThrow(() -> new HttpNotFound("bus not found"));
        if(busRepository.existsBusByNameAndIdNot(busCreateRequest.getName(), id)) {
            throw new HttpConflict("duplicate name");
        }
        bus.setName(busCreateRequest.getName());
        bus.setDescriptions(busCreateRequest.getDescriptions());
        bus.setLicensePlate(busCreateRequest.getLicensePlate());
        bus.setCapacity(busCreateRequest.getCapacity());
        bus.setBusCompany(busCompanyService.findBusCompanyById(busCreateRequest.getBusCompanyId()));
        bus.setCreatedAt(LocalDateTime.now());
        if(busCreateRequest.getBusImage() != null && !busCreateRequest.getBusImage().isEmpty()){
            for (MultipartFile file : busCreateRequest.getBusImage()){
                String imageUrl = cloudinaryService.upload(file);
                BusImage busImage = new BusImage();
                busImage.setImageUrl(imageUrl);
                busImage.setBus(bus);
                bus.getImages().add(busImage);
            }
        }else {
            bus.setImages(bus.getImages());
        }
        bus.setId(id);
        Bus newBus = busRepository.save(bus);
        String busImage = newBus.getImages().stream().map(BusImage::getImageUrl).collect(Collectors.joining(","));
        return BusResponse.builder()
                .descriptions(newBus.getDescriptions())
                .name(newBus.getName())
                .licensePlate(newBus.getLicensePlate())
                .capacity(newBus.getCapacity())
                .createdAt(LocalDateTime.now())
                .busCompanyName(newBus.getBusCompany().getCompanyName())
                .busImage(busImage)
                .build();
    }

    @Override
    public String delete(int id) {
        Bus bus = busRepository.findById(id).orElseThrow(() -> new HttpNotFound("id not found"));
        busRepository.delete(bus);
        return "xoa thanh cong";
    }

    @Override
    public Page<BusListResponse> findAllByPage(Pageable pageable, String search) {
        Page<Bus> list = busRepository.findAllAndSearch(pageable, search);
        return list.map(e -> BusListResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .description(e.getDescriptions())
                .capacity(e.getCapacity())
                .lisensePlate(e.getLicensePlate())
                .build());
    }




}
