package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.dto.resp.BusResponse;
import com.ra.base_spring_boot.model.Bus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
    public interface IBusRepository extends JpaRepository<Bus,Integer> {

        @Query("select b from Bus b where b.name like concat('%' , :search , '%') ")
        Page<Bus> findAllAndSearch(Pageable pageable , String search);

        boolean existsBusByName(String busName);

        boolean existsBusByNameAndIdNot(String busName, Integer busId);

        Bus findBusesById(int busId);

        @Query("select count(b) > 0 from Schedule b where b.bus.id = :busId")
        boolean hasRelatedSchedule(@Param("busId") Integer busId);

        @Query("select count(s) > 0 from Seat s where s.bus.id = :busId")
        boolean hasRelatedSeats(@Param("busId") Integer busId);

        @Query("select count(b) > 0 from BusImage b where b.bus.id = :busId")
        boolean hasRelatedImage(@Param("busId") Integer busId);

        @Query("select count(b) > 0 from BusReview b where b.bus.id = :busId")
        boolean hasRelatedReviews(@Param("busId") Integer busId);


    }



