package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.converter.BannerConverter;
import com.ra.base_spring_boot.dto.req.BannerRequest;
import com.ra.base_spring_boot.dto.resp.BannerResponse;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.Banner;
import com.ra.base_spring_boot.repository.IBannerRepository;
import com.ra.base_spring_boot.services.IBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements IBannerService {
    private final IBannerRepository bannerRepository;
    private final BannerConverter bannerConverter;
    private final CloudinaryServiceImpl cloudinaryService;

    @Override
    public List<BannerResponse> findAllBanner() {
        return bannerConverter.toDTOList(bannerRepository.findAll());
    }

    @Override
    public Page<BannerResponse> getAllBanner (String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = null;
        }else {
            keyword = keyword.trim().toLowerCase();
        }
        return bannerRepository.getAllAndSearchBanner(keyword,pageable)
                .map(BannerResponse::new);
    }
    @Override
    public BannerResponse createBanner (BannerRequest request) {
        if (bannerRepository.existsByPosition(request.getPosition())) {
            throw new HttpConflict("Position banner already exists!");
        }
        try {

            String imageUrl = cloudinaryService.upload(request.getBannerUrl());
            Banner banner = new Banner();
            banner.setBannerUrl(imageUrl);
            banner.setPosition(request.getPosition());
            bannerRepository.save(banner);
            return new BannerResponse(banner);

        } catch (Exception e) {
            throw new HttpBadRequest("Upload banner failed");
        }
    }

    @Override
    public void deleteBanner (Integer id){
        Banner banner = bannerRepository.findById(id).orElseThrow(()-> new HttpNotFound("Banner not found with id: "+id));
       bannerRepository.deleteById(id);
    }
    @Override
    public BannerResponse getBannerById (Integer id) {
        Banner banner = bannerRepository.findById(id).orElseThrow(()-> new HttpNotFound("Banner not found with id: "+id));
        return BannerResponse.builder()
                .id(banner.getId())
                .bannerUrl(banner.getBannerUrl())
                .position(banner.getPosition())
                .createdAt(banner.getCreatedAt())
                .updatedAt(banner.getUpdatedAt())
                .build();

    }

}
