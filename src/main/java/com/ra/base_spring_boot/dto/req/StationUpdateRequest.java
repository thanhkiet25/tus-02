package com.ra.base_spring_boot.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StationUpdateRequest {

    private String name;
    private MultipartFile imageFile;
    private MultipartFile wallpaperFile;
    private String descriptions;
    private String location;
}
