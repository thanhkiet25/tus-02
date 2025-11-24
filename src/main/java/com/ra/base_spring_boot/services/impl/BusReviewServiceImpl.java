package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.resp.PopularBusReviewResponse;
import com.ra.base_spring_boot.model.BusReview;
import com.ra.base_spring_boot.repository.IBusReviewRepository;
import com.ra.base_spring_boot.services.IBusReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BusReviewServiceImpl implements IBusReviewService {
    private final IBusReviewRepository busReviewRepository;
    @Override
    public List<PopularBusReviewResponse> getBusReviews(int limit) {
        return busReviewRepository.getPopularBusReviews(PageRequest.of(0, limit));
    }

    @Override
    public Set<BusReview> findBusReviewsByBusId(List<Integer> id) {
        Set<BusReview> busReviews = new HashSet<>();
        for (Integer i : id) {
            busReviews.add(busReviewRepository.findBusReviewById(i));
        }
        return busReviews;
    }
}
