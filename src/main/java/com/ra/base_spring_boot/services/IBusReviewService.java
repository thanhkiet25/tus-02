package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.PopularBusReviewResponse;

import java.util.List;

public interface IBusReviewService {
    List<PopularBusReviewResponse>  getBusReviews(int limit);
}
