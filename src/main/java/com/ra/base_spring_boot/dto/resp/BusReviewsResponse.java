package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.BusReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BusReviewsResponse {
    private int busReviewId;
    private Integer rating;
    private String review;
    private LocalDateTime createdAt;
    private int busId;
    private String name;

    public BusReviewsResponse(BusReview busReview){
        this.busReviewId = busReview.getId();
        this.rating = busReview.getRating();
        this.review = busReview.getReview();
        this.createdAt = busReview.getCreatedAt();
        this.busId = busReview.getBus().getId();
        this.name = busReview.getBus().getName();
    };
}
