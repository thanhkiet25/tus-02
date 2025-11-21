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
public class StationCreateRequest {

    @NotBlank(message = "Station name is required")
    private String name;

    private MultipartFile imageFile;
    private MultipartFile wallpaperFile;
    private String descriptions;

    @NotBlank(message = "Location is required")
    private String location;
}




