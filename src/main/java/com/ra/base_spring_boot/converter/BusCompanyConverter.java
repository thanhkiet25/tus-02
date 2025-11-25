package com.ra.base_spring_boot.converter;

import com.ra.base_spring_boot.dto.req.BusCompanyRequest;

import com.ra.base_spring_boot.dto.resp.BusCompanyResponse;

import com.ra.base_spring_boot.model.BusCompany;
import org.springframework.stereotype.Component;



@Component
public class BusCompanyConverter {
    public BusCompany toBusCompany(BusCompanyRequest companyRequest) {
        return  BusCompany.builder()
                .companyName(companyRequest.getCompanyName())
                .image(companyRequest.getCompanyImage())
                .descriptions(companyRequest.getDescription())
                .build();

    }

    public BusCompanyResponse toBusCompanyResponse(BusCompany busCompany) {
        return BusCompanyResponse.builder()
                .id(busCompany.getId())
                .companyName(busCompany.getCompanyName())
                .image(busCompany.getImage())
                .descriptions(busCompany.getDescriptions())
                .build();
    }

}
