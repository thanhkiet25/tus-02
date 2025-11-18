package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.BannerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IBannerService {
    List<BannerDTO> findAllBanner();
}
