package com.ra.base_spring_boot.repository;
import com.ra.base_spring_boot.model.Route;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ra.base_spring_boot.dto.resp.PopularRoutesResponse;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IRouteRepository extends JpaRepository<Route,Integer> {
    Page<Route> findAll(Pageable pageable);

    @Query("""
select new com.ra.base_spring_boot.dto.resp.PopularRoutesResponse(
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
    List<PopularRoutesResponse> getPopularRouter(Pageable pageable);

    @Query("""
select r from Route r 
where (:keyword is null
or lower(r.departureStation.name) like lower(concat('%', :keyword,'%') )
 or lower(r.arrivalStation.name) like lower(concat('%', :keyword, '%') ) )
""")
    Page<Route> findAllAndSearchRoute(@Param("keyword") String keyword, Pageable pageable);
    Optional<Route> findById(int id);
    //KT route ton tai
    @Query("""
    SELECT r FROM Route r
    WHERE r.departureStation.id = :departureId
      AND r.arrivalStation.id = :arrivalId
      AND (:excludeId IS NULL OR r.id <> :excludeId)
    """)
    Optional<Route> findDuplicateRoute(
            @Param("departureId") Integer departureId,
            @Param("arrivalId") Integer arrivalId,
            @Param("excludeId") Integer excludeId
    );

}


