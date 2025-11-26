package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.BusReviewCreateResponse;
import com.ra.base_spring_boot.dto.resp.BusReviewsResponse;
import com.ra.base_spring_boot.dto.resp.PopularBusReviewResponse;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.Bus;
import com.ra.base_spring_boot.model.BusReview;
import com.ra.base_spring_boot.repository.IBusReviewRepository;
import com.ra.base_spring_boot.services.IBusReviewService;
import com.ra.base_spring_boot.services.IBusService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BusReviewServiceImpl implements IBusReviewService {
    private final IBusReviewRepository busReviewRepository;
    private final IBusService busService;
    @Override
    public List<PopularBusReviewResponse> getBusReviews(int limit) {
        return busReviewRepository.getPopularBusReviews(PageRequest.of(0, limit));
    }

    //tra ve busReview
    @Override
    public Page<BusReviewsResponse> findAllSearchAndFilter(String search,String filterBus,Pageable pageable) {
        String trimmedSearch = search.trim();
        String  trimmedFilterBus = filterBus.trim();
        Page<BusReview> list = busReviewRepository.searchBusAndFilter(trimmedSearch, trimmedFilterBus, pageable);
        return list.map(BusReviewsResponse::new);

    }

    @Override
    public Page<BusReviewsResponse> findAllBusReviews(Pageable pageable , Integer rating) {
        Page<BusReview> list = busReviewRepository.findAllBusReviews(pageable, rating);
        return list.map(BusReviewsResponse::new);
    }

    @Override
    public BusReviewsResponse saveBusReview(BusReviewCreateResponse busReviewCreateResponse) {
      BusReview busReview = new BusReview();
        if (busReview == null) {
            throw new RuntimeException("Bus review không tồn tại với ID: " + busReview.getId());
        }

        int busId = busReviewCreateResponse.getBusId();
        Bus bus = busService.findBusById(busId);
        if (bus == null) {
            throw new RuntimeException("BusId: " + busId + "not found");
        }
        busReview.setBus(bus);

        Integer rating = busReviewCreateResponse.getRating();
        if (rating == null) {
            throw new HttpBadRequest("Rating không được để trống");
        }
        if (rating > 10) {
            throw new HttpBadRequest("Rating max is 10");
        }
        if (rating < 0) {
            throw new HttpBadRequest("Rating min is 0");
        }
      busReview.setRating(rating);
      busReview.setReview(busReviewCreateResponse.getReview());
      busReview.setId(busReview.getId());

      BusReview newBusReview = busReviewRepository.save(busReview);
        return new BusReviewsResponse(newBusReview);
    }

    @Override
    public String deleteBusReview(int id) {
        BusReview busReview = busReviewRepository.findById(id).orElseThrow(() -> new HttpNotFound("bus id: " + id + " not found"));
        busReviewRepository.delete(busReview);
        return "delete success";
    }

}
