package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.BusReviewCreateResponse;
import com.ra.base_spring_boot.dto.resp.BusReviewsResponse;
import com.ra.base_spring_boot.dto.resp.PopularBusReviewResponse;
import com.ra.base_spring_boot.exception.HttpNotFound;
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

        Page<BusReview> list = busReviewRepository.searchBusAndFilter(search, filterBus, pageable);
       return list.map(BusReviewsResponse::new);
    }

    @Override
    public Page<BusReviewsResponse> findAllBusReviews(Pageable pageable) {
        Page<BusReview> list = busReviewRepository.findAll(pageable);
        return list.map(e -> BusReviewsResponse.builder()
                .busReviewId(e.getId())
                .busId(e.getBus().getId())
                .rating(e.getRating())
                .review(e.getReview())
                .createdAt(e.getCreatedAt())
                .build());
    }

    @Override
    public BusReviewsResponse saveBusReview(BusReviewCreateResponse busReviewCreateResponse) {
      BusReview busReview = new BusReview();
        if (busReview == null) {
            throw new RuntimeException("Bus review không tồn tại với ID: " + busReview.getId());
        }
      busReview.setRating(busReviewCreateResponse.getRating());
      busReview.setReview(busReviewCreateResponse.getReview());
      busReview.setId(busReview.getId());
      busReview.setBus(busService.findBusById(busReviewCreateResponse.getBusId()));

      BusReview newBusReview = busReviewRepository.save(busReview);
      return BusReviewsResponse.builder()
              .busReviewId(newBusReview.getId())
              .busId(newBusReview.getBus().getId())
              .rating(newBusReview.getRating())
              .review(newBusReview.getReview())
              .createdAt(LocalDateTime.now())
              .build();
    }

    @Override
    public String deleteBusReview(int id) {
        BusReview busReview = busReviewRepository.findById(id).orElseThrow(() -> new HttpNotFound("bus id: " + id + " not found"));
        busReviewRepository.delete(busReview);
        return "delete success";
    }

}
