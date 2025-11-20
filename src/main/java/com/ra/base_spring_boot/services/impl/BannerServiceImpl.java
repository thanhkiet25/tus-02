package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.converter.BannerConverter;
import com.ra.base_spring_boot.dto.resp.BannerResponse;
import com.ra.base_spring_boot.repository.IBannerRepository;
import com.ra.base_spring_boot.services.IBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements IBannerService {
    private final IBannerRepository bannerRepository;
    private final BannerConverter bannerConverter;

    @Override
    public List<BannerResponse> findAllBanner() {
        return bannerConverter.toDTOList(bannerRepository.findAll());
    }


}
