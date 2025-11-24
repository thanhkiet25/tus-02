package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.PopularBusReviewResponse;
import com.ra.base_spring_boot.model.BusReview;
import java.util.List;
import java.util.Set;

public interface IBusReviewService {
    List<PopularBusReviewResponse>  getBusReviews(int limit);


}
