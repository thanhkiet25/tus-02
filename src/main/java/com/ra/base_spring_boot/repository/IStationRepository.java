package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.dto.resp.PopularStationResponse;
import com.ra.base_spring_boot.model.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface IStationRepository extends JpaRepository<Station,Integer> {
    @Query("select s from Station s where s.name like concat('%' , :search , '%') ")
    Page<Station> findAllAndSearch(Pageable pageable , String search);

    Station findStationById(int id);

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