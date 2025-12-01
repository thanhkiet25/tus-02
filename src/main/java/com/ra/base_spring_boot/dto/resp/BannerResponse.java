package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.Banner;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BannerResponse {
    private Integer id;
    private String bannerUrl;
    private String position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BannerResponse(Banner banner) {
        this.id = banner.getId();
        this.bannerUrl = banner.getBannerUrl();
        this.position = banner.getPosition();
        this.createdAt = banner.getCreatedAt();
        this.updatedAt = banner.getUpdatedAt();
    }
}
