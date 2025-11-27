package com.ra.base_spring_boot.converter;
import com.ra.base_spring_boot.dto.resp.BusCompanyResponse;
import com.ra.base_spring_boot.model.BusCompany;
import org.springframework.stereotype.Component;

@Component
public class BusCompanyConverter {
    public BusCompanyResponse toBusCompanyResponse(BusCompany busCompany) {
        return BusCompanyResponse.builder()
                .id(busCompany.getId())
                .companyName(busCompany.getCompanyName())
                .companyImage(busCompany.getImage())
                .description(busCompany.getDescriptions())
                .build();
    }

}
