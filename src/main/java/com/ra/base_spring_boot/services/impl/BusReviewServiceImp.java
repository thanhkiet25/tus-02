package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.resp.PopularBusReviewDTO;
import com.ra.base_spring_boot.repository.IBusCompanyRepository;
import com.ra.base_spring_boot.repository.IBusReviewRepository;
import com.ra.base_spring_boot.services.IBusReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusReviewServiceImp implements IBusReviewService {
    private final IBusReviewRepository busReviewRepository;
    @Override
    public List<PopularBusReviewDTO> getBusReviews(int limit) {
        return busReviewRepository.getPopularBusReviews(PageRequest.of(0, limit));
    }
}
