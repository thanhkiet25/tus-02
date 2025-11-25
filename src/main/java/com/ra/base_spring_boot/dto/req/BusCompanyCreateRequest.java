package com.ra.base_spring_boot.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BusCompanyCreateRequest {
    @NotBlank(message = "BusCompany is required")
    private String companyName;
    private MultipartFile imageFile;
    private String description;
}
