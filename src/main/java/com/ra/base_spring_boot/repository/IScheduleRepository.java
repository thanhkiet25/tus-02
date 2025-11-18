package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.dto.resp.SearchScheduleDTO;
import com.ra.base_spring_boot.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IScheduleRepository extends JpaRepository<Schedule,Integer> {
    @Query("""
select new com.ra.base_spring_boot.dto.resp.SearchScheduleDTO(
  s.id,
  ds.name,
  arr.name,
  s.departureTime
)
from Schedule s
join s.route r
join r.departureStation ds
join r.arrivalStation arr
where (:fromId is null or ds.id = :fromId)
and   (:toId is null or arr.id = :toId)
and   (:date is null or date(s.departureTime)= :date)
order by s.departureTime asc
""")
    List<SearchScheduleDTO> searchSchedule(
            @Param("fromId") Long fromId,
            @Param("toId") Long toId,
            @Param("date") LocalDate date
            );
}
