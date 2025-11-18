package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.PopularBusReviewDTO;

import java.util.List;

public interface IBusReviewService {
    List<PopularBusReviewDTO>  getBusReviews(int limit);
}
