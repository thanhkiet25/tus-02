package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.BannerResponse;

import java.util.List;


public interface IBannerService {
    List<BannerResponse> findAllBanner();
}
