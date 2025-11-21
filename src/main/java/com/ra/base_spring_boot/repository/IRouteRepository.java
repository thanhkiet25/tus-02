package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.dto.resp.PopularRouteResponse;
import com.ra.base_spring_boot.model.Route;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface IRouteRepository extends JpaRepository<Route,Integer> {
    Page<Route> findAll(Pageable pageable);

    Route findById(int id);

    @Query("""
select new com.ra.base_spring_boot.dto.resp.PopularRouteResponse(
  r.id,
  ds.name,
  arr.name,
  count(t.id)
)
from Ticket t
join t.schedule sch
join sch.route r
join r.departureStation ds
join r.arrivalStation arr
group by r.id,ds.name,arr.name
order by count(t.id) desc
""")
    List<PopularRouteResponse> getPopularRouter(Pageable pageable);
}
