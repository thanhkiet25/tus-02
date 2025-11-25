package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.dto.resp.PopularBusReviewResponse;
import com.ra.base_spring_boot.model.BusReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBusReviewRepository extends JpaRepository<BusReview, Integer> {
    @Query("""
select  new com.ra.base_spring_boot.dto.resp.PopularBusReviewResponse(
    r.id,
    r.rating,
    r.review,
    b.name,
    ds.name,
    arr.name,
    r.createdAt
    )
from BusReview r
join r.bus b
join b.schedules sch
join sch.route rt
join rt.departureStation ds
join rt.arrivalStation arr
order by r.rating desc , r.createdAt desc
""")
    List<PopularBusReviewResponse> getPopularBusReviews(Pageable pageable);

    BusReview findBusReviewById(int id);

}
