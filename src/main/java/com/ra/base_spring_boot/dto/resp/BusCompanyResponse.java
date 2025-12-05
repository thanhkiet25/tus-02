package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.BusCompany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BusCompanyResponse {
    private int id;
    private String companyName;
    private String companyImage;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BusCompanyResponse(BusCompany busCompany) {
        this.id = busCompany.getId();
        this.companyName = busCompany.getCompanyName();
        this.companyImage=busCompany.getImage();
        this.description=busCompany.getDescriptions();
        this.createdAt=busCompany.getCreatedAt();
        this.updatedAt=busCompany.getUpdatedAt();
    }
}
