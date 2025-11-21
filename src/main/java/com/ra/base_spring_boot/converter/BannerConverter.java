package com.ra.base_spring_boot.converter;

import com.ra.base_spring_boot.dto.resp.BannerResponse;
import com.ra.base_spring_boot.model.Banner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BannerConverter {
    public BannerResponse toDTO(Banner banner) {
        return BannerResponse.builder()
                .id(banner.getId())
                .bannerUrl(banner.getBannerUrl())
                .position(banner.getPosition())
                .build();
    }
    public List<BannerResponse> toDTOList(List<Banner> banners) {
        return banners.stream()
                .map(this::toDTO)
                .toList();

    }
}
