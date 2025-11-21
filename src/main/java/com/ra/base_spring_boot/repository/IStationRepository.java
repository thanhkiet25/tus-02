package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.dto.resp.PopularStationResponse;
import com.ra.base_spring_boot.model.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface IStationRepository extends JpaRepository<Station,Integer> {
    @Query("SELECT s FROM Station s WHERE " +
            "(:search IS NULL OR :search = '') OR " +
            "(LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.location) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Station> findAllAndSearch(@Param("search") String search, Pageable pageable);

    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);

    @Query("SELECT COUNT(r) > 0 FROM Route r WHERE r.departureStation.id = :stationId OR r.arrivalStation.id = :stationId")
    boolean hasRelatedRoutes(@Param("stationId") Integer stationId);


    @Query("""
select new com.ra.base_spring_boot.dto.resp.PopularStationResponse(
  ds.id,
  ds.name,
  count(t.id)
)
from Ticket t
join t.schedule sch
join sch.route r
join r.departureStation ds  
group by ds.id,ds.name
order by count(t.id) desc
""")
    List<PopularStationResponse> getPopularStations(Pageable pageable);

}