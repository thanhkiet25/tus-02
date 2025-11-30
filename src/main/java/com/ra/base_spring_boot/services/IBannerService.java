package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.req.BannerRequest;
import com.ra.base_spring_boot.dto.resp.BannerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IBannerService {
    List<BannerResponse> findAllBanner();

    Page<BannerResponse> getAllBanner(String keyword, Pageable pageable);
    BannerResponse createBanner(BannerRequest request);
    void deleteBanner(Integer id);
}
