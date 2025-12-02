package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.BusCreateRequest;
import com.ra.base_spring_boot.dto.resp.BusResponse;
import com.ra.base_spring_boot.dto.resp.BusListResponse;
import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.*;
import com.ra.base_spring_boot.repository.IBusRepository;
import com.ra.base_spring_boot.repository.IScheduleRepository;
import com.ra.base_spring_boot.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements IBusService {
    private final IBusRepository busRepository;

    private final CloudinaryServiceImpl cloudinaryService;

    private final IBusCompanyService busCompanyService;

    private final IScheduleRepository  scheduleRepository;



    @Override
    public BusResponse save(BusCreateRequest busReqDto) {
        if(busRepository.existsBusByBusName(busReqDto.getName())){
            throw new HttpConflict("duplicate name");
        }
        if(busRepository.existsBusByLicensePlate(busReqDto.getLicensePlate())){
            throw new HttpConflict("duplicate licensePlate");
        }
        Bus bus = new Bus();
        bus.setBusName(busReqDto.getName());
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
                .id(saveBus.getId())
                .name(saveBus.getBusName())
                .licensePlate(saveBus.getLicensePlate())
                .capacity(saveBus.getCapacity())
                .busCompanyName(saveBus.getBusCompany().getCompanyName())
                .busImage(listImage)
                .descriptions(saveBus.getDescriptions())
                .build();
    }

    @Override
    public BusResponse update(BusCreateRequest busCreateRequest, int id) {
        Bus bus = busRepository.findById(id).orElseThrow(() -> new HttpNotFound("busId: " + id + " not found"));
        if(busRepository.existsBusByBusNameAndIdNot(busCreateRequest.getName(), id)) {
            throw new HttpConflict("duplicate name");
        }
        if(busRepository.existsBusByLicensePlateAndIdNot(busCreateRequest.getLicensePlate(), id)) {
            throw new HttpConflict("duplicate LicensePlate");
        }
        bus.setBusName(busCreateRequest.getName());
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
            }
        }else {
            bus.setImages(bus.getImages());
        }
        bus.setId(id);
        Bus newBus = busRepository.save(bus);
        String busImage = newBus.getImages().stream().map(BusImage::getImageUrl).collect(Collectors.joining(","));
        return BusResponse.builder()
                .id(id)
                .descriptions(newBus.getDescriptions())
                .name(newBus.getBusName())
                .licensePlate(newBus.getLicensePlate())
                .capacity(newBus.getCapacity())
                .busCompanyName(newBus.getBusCompany().getCompanyName())
                .busImage(busImage)
                .build();
    }

    @Override
    public void delete(int id) {
        Bus bus = busRepository.findById(id).orElseThrow(() -> new HttpNotFound("busId: " + id + " not found"));
    if(busRepository.hasRelatedSchedule(bus.getId()) || busRepository.hasRelatedReviews(bus.getId()) || busRepository.hasRelatedImage(bus.getId()) || busRepository.hasRelatedSeats(bus.getId())) {
        throw new HttpConflict ("Can not delete bus, please remove Schedule | Reviews | Image | Seats.");
    }
        busRepository.deleteById(id);
    }

    @Override
    public Page<BusListResponse> findAllByPage(Pageable pageable, String search) {
        String trimmedSearch = search.trim();
        Page<Bus> list = busRepository.findAllAndSearch(pageable, trimmedSearch);
        return list.map(e -> BusListResponse.builder()
                .id(e.getId())
                .name(e.getBusName())
                .description(e.getDescriptions())
                .capacity(e.getCapacity())
                .lisensePlate(e.getLicensePlate())
                .build());
    }

    @Override
    public BusResponse findBusResponseById(int id) {
        Bus bus =  busRepository.findById(id).orElseThrow(() -> new HttpNotFound("bus id: " + id + " not found"));
        return BusResponse.builder()
                .id(bus.getId())
                .name(bus.getBusName())
                .descriptions(bus.getDescriptions())
                .capacity(bus.getCapacity())
                .busImage(bus.getImages().stream().map(BusImage::getImageUrl).collect(Collectors.joining(",")))
                .busCompanyName(bus.getBusCompany().getCompanyName())
                .licensePlate(bus.getLicensePlate())
                .build();
    }

    @Override
    public Bus findBusById(int id) {
        return busRepository.findById(id).orElseThrow(() -> new HttpNotFound("bus id: " + id + " not found"));
    }


}
