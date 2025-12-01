package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.req.BusReviewCreateResponse;
import com.ra.base_spring_boot.dto.resp.BusReviewsResponse;
import com.ra.base_spring_boot.dto.resp.PopularBusReviewResponse;
import com.ra.base_spring_boot.model.BusReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface IBusReviewService {
    List<PopularBusReviewResponse>  getBusReviews(int limit);

    //tra ve busReview
    Page<BusReviewsResponse> findAllSearchAndFilter( String search,String filterBus ,Pageable pageable);

    Page<BusReviewsResponse> findAllBusReviews(Pageable pageable , Integer search);

    BusReviewsResponse saveBusReview(BusReviewCreateResponse busReviewCreateResponse);

    String deleteBusReview(int id);
}
