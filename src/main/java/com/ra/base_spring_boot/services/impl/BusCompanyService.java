package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.resp.BusCompanyDetailResponse;
import com.ra.base_spring_boot.dto.resp.BusCompanyResponse;
import com.ra.base_spring_boot.dto.resp.PageResponse;
import com.ra.base_spring_boot.model.BusCompany;
import com.ra.base_spring_boot.repository.BusCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BusCompanyService {
    @Autowired
    private BusCompanyRepository busCompanyRepository;
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
    public BusCompanyDetailResponse getCompanyById(int id) {
        BusCompany company = busCompanyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà xe với id:" + id));

        return BusCompanyDetailResponse.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .image(company.getImage())
                .descriptions(company.getDescriptions())
                .build();
    }


}
