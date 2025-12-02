package com.ra.base_spring_boot.repository;
import com.ra.base_spring_boot.dto.resp.SearchScheduleResponse;
import com.ra.base_spring_boot.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public interface IScheduleRepository extends JpaRepository<Schedule,Integer> {
    @Query(value="""
        SELECT
            s.id as schedule_id,
            ds.name as departure_station,
            arr.name as arrival_station,
            bc.company_name as company_name,
            b.name as bus_name,
            bc.image as bus_company_image,
            b.capacity,
            s.available_seat,
            r.price,
            s.departure_time,
            s.arrival_time
        FROM schedules s
        JOIN routes r on s.route_id = r.id
        JOIN stations ds on r.departure_station_id = ds.id
        JOIN stations arr on r.arrival_station_id = arr.id
        JOIN buses b on s.bus_id = b.id
        JOIN bus_companies bc on b.company_id = bc.id
        WHERE (:fromId IS NULL OR ds.id = :fromId)
        AND   (:toId IS NULL OR arr.id = :toId)
        AND   (:date IS NULL OR DATE(s.departure_time) = :date)
        AND   s.status = 'AVAILABLE'
        ORDER BY s.departure_time ASC
    """,nativeQuery = true)
    List<Object[]> searchSchedule(
            @Param("fromId") Long fromId,
            @Param("toId") Long toId,
            @Param("date") LocalDate date
            );

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
