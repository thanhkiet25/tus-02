package com.ra.base_spring_boot.services.impl;
import com.ra.base_spring_boot.dto.req.BusCompanyUpdateRequest;
import com.ra.base_spring_boot.dto.resp.BusCompanyResponse;
import com.ra.base_spring_boot.dto.resp.PageResponse;
import com.ra.base_spring_boot.dto.resp.PopularBusCompanyResponse;
import com.ra.base_spring_boot.converter.BusCompanyConverter;
import com.ra.base_spring_boot.dto.req.BusCompanyCreateRequest;
import com.ra.base_spring_boot.dto.resp.StatisticBusCompany;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.BusCompany;
import com.ra.base_spring_boot.repository.IBusCompanyRepository;
import com.ra.base_spring_boot.services.IBusCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
@Service
public class BusCompanyServiceImpl implements IBusCompanyService {
    @Autowired
    private  IBusCompanyRepository busCompanyRepository;
    @Autowired
    private BusCompanyConverter busCompanyConverter;
    @Autowired
    private CloudinaryServiceImpl cloudinaryService;

    public PageResponse<BusCompanyResponse> getCompanyNames(Pageable pageable) {
        Page<BusCompanyResponse> page = busCompanyRepository.findAll(pageable)
                .map(company -> BusCompanyResponse
                        .builder()
                        .id(company.getId())
                        .companyName(company.getCompanyName())
                        .build()
                );
        return PageResponse
                .<BusCompanyResponse>builder()
                .data(page.getContent())
                .currentPage(page.getNumber())
                .totalElements(page.getTotalElements())
                .build();
    }



    public BusCompanyResponse getCompanyById(int id) {
        BusCompany company = busCompanyRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Bus Company not found with id: " + id));

        return BusCompanyResponse.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .companyImage(company.getImage())
                .description(company.getDescriptions())
                .build();
    }

    public List<PopularBusCompanyResponse> getPopularBusCompanyDTO(int limit) {
        return busCompanyRepository.getPopularBusCompany(PageRequest.of(0, limit));
    }



    @Override
    public Page<BusCompanyResponse> findBusCompanyByNameAndGetAllAndSort(String search,int page,int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<BusCompany> companyPage =
                (search == null || search.trim().isEmpty()) ?
                        busCompanyRepository.findAll(pageable) :
                        busCompanyRepository.findByCompanyNameContainingIgnoreCase(search, pageable);

        return companyPage.map(busCompanyConverter::toBusCompanyResponse);

    }

    @Override
    public BusCompanyResponse getById(Integer id) {
        BusCompany busCompany= busCompanyRepository.findById(id).orElseThrow(()-> new HttpNotFound("Bus Company Not Found"));
    return new  BusCompanyResponse(busCompany);
    }

    @Override
    public BusCompanyResponse createBusCompany(BusCompanyCreateRequest busCompanyRequest) {
        String newName = busCompanyRequest.getCompanyName();
        if(busCompanyRepository.existsByCompanyName(newName)){
            throw new HttpConflict("CompanyName already exists");
        }
        BusCompany busCompany = new BusCompany();
        busCompany.setCompanyName(newName);
        busCompany.setDescriptions(busCompanyRequest.getDescription());

        if(busCompanyRequest.getImageFile()!=null && !busCompanyRequest.getImageFile().isEmpty()){
            String images = cloudinaryService.upload(busCompanyRequest.getImageFile());
            busCompany.setImage(images);
        }
        BusCompany busCompanySaved = busCompanyRepository.save(busCompany);
        return new BusCompanyResponse(busCompanySaved);
    }


@Override
    public BusCompanyResponse updateBusCompany(int id, BusCompanyUpdateRequest busCompanyRequest) {
        BusCompany busCompany = busCompanyRepository.findById(id).orElseThrow(() -> new HttpNotFound("Bus Company Not Found"));
        boolean isModified = false;
        String rawName = busCompanyRequest.getCompanyName();
        if(rawName!=null) {
            String newName = rawName.trim();
            if (!newName.isEmpty()) {
                if (!busCompany.getCompanyName().equals(newName)) {
                    if (busCompanyRepository.existsByCompanyNameAndIdNot(newName, id)) {
                        throw new HttpConflict("CompanyName already exists");
                    }
                }
                busCompany.setCompanyName(newName);
                isModified = true;
            }
        }

        if(busCompanyRequest.getImageFile()!=null && !busCompanyRequest.getImageFile().isEmpty()){
            String images = cloudinaryService.upload(busCompanyRequest.getImageFile());
            busCompany.setImage(images);
             isModified = true;
        }
        if(StringUtils.hasText(busCompanyRequest.getDescription())) {
            busCompany.setDescriptions(busCompanyRequest.getDescription());
            isModified = true;
        }
        if(!isModified) {
            throw new HttpBadRequest("No changes detected");
        }
        BusCompany busCompanyUpdated = busCompanyRepository.save(busCompany);
        return new BusCompanyResponse(busCompanyUpdated);

    }

    @Override
    public BusCompany findBusCompanyById(int id) {
        return busCompanyRepository.findById(id).orElseThrow(() -> new HttpNotFound("Bus Company Not Found"));
    }

    @Override
    public BusCompanyResponse deleteBusCompany(int id) {
       BusCompany busCompany = busCompanyRepository.findById(id).orElseThrow(() -> new HttpNotFound("Bus Company Not Found"));
       if(busCompanyRepository.hasBusCompany(id)){
          throw  new HttpConflict("cannot delete Bus Company because it is used in bus, please remove related bus first");
       }
       BusCompanyResponse busCompanyResponse = new BusCompanyResponse(busCompany);
       busCompanyRepository.delete(busCompany);
       return busCompanyResponse;

    }

    @Override
    public Page<StatisticBusCompany> getStatisticBusCompany(Pageable pageable) {
        return busCompanyRepository.getStatisticBusCompany(pageable)
                .map(rows -> new StatisticBusCompany(
                        ((Number) rows[0]).intValue(),
                        ((String) rows[1]),
                        ((Number) rows[2]).intValue(),
                        ((Number) rows[3]).intValue()
                ));
    }


}







