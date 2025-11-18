package com.ra.base_spring_boot.converter;

import com.ra.base_spring_boot.dto.resp.BannerDTO;
import com.ra.base_spring_boot.model.Banner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BannerConverter {
    public BannerDTO toDTO(Banner banner) {
        return BannerDTO.builder()
                .id(banner.getId())
                .bannerUrl(banner.getBannerUrl())
                .position(banner.getPosition())
                .build();
    }
    public List<BannerDTO> toDTOList(List<Banner> banners) {
        return banners.stream()
                .map(this::toDTO)
                .toList();

    }
}
