package com.ra.base_spring_boot.repository;
import com.ra.base_spring_boot.dto.resp.SearchScheduleResponse;
import com.ra.base_spring_boot.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public interface IScheduleRepository extends JpaRepository<Schedule,Integer> {
    @Query("""
        SELECT new com.ra.base_spring_boot.dto.resp.SearchScheduleResponse(
            s.id,
            ds.name,
            arr.name,
            bc.companyName,
            b.busName,
            bc.image,
            b.capacity,
            s.availableSeat,
            r.price,
            s.departureTime,
            s.arrivalTime
        )
        FROM Schedule s
        JOIN s.route r
        JOIN s.bus b
        JOIN b.busCompany bc
        JOIN r.departureStation ds
        JOIN r.arrivalStation arr
        WHERE (:fromId IS NULL OR ds.id = :fromId)
        AND   (:toId IS NULL OR arr.id = :toId)
        AND   (:date IS NULL OR CAST(s.departureTime AS DATE) = :date)
        AND   s.status = 'AVAILABLE'
        ORDER BY s.departureTime ASC
    """)
    List<SearchScheduleResponse> searchSchedule(
            @Param("fromId") Long fromId,
            @Param("toId") Long toId,
            @Param("date") LocalDate date
            );

    @Modifying
    @Transactional
    @Query("UPDATE Schedule s SET s.availableSeat = s.availableSeat + 1 WHERE s.id = :scheduleId")
    void incrementAvailableSeat(@Param("scheduleId") Integer scheduleId);

    Schedule findScheduleById(Integer id);

  @Query("""
select sch from Schedule sch
join sch.route r
join r.departureStation ds
join r.arrivalStation arr
join sch.bus b
where
(:keyword is null or
ds.name like %:keyword% or
arr.name like %:keyword% or
b.busName like %:keyword%)
""")
    Page<Schedule> searchSchedule(@Param("keyword")  String keyword, Pageable pageable);

  // create check giờ đi - đến của bus ở lịch trình cũ và mới không đuọc trung nhau
    @Query("""
select count(sch) from Schedule sch
where sch.bus.id = :busId
and(:departureTime < sch.arrivalTime and :arrivalTime >sch.departureTime )
""")
    int overLappingSchedules(
            @Param("busId") Integer busId,
            @Param("departureTime")LocalDateTime departureTime,
            @Param("arrivalTime")LocalDateTime arrivalTime
            );

    // update check giờ đi - đến của bus ở lịch trình cũ và mới không đuọc trung nhau
    @Query("""
select count(sch) from Schedule sch
where sch.bus.id = :busId
and sch.id <> :scheduleId
and(:departureTime < sch.arrivalTime and :arrivalTime >sch.departureTime )
""")
    int overLappingSchedules(
            @Param("busId") Integer busId,
            @Param("scheduleId") Integer scheduleId,
            @Param("departureTime")LocalDateTime departureTime,
            @Param("arrivalTime")LocalDateTime arrivalTime
    );
}
