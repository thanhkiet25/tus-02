package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.BusCompany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BusCompanyListResponse {
    private int id;
    private String companyName;
    private String image;
    private String descriptions;

    public BusCompanyListResponse(BusCompany busCompany) {
        this.id = busCompany.getId();
        this.companyName = busCompany.getCompanyName();
        this.image = busCompany.getImage();
        this.descriptions = busCompany.getDescriptions();
    }
}
