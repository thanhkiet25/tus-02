package com.ra.base_spring_boot.dto.req;

import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BusCompanyUpdateRequest {
    private String companyName;
    private MultipartFile imageFile;
    private String description;
}
