package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.BusCompany;
import com.ra.base_spring_boot.model.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ISeatRepository extends JpaRepository<Seat,Integer> {
    Page<Seat> findAllByBus_BusCompany_IdAndBus_NameContaining(Integer busBusCompanyId, String searchBusName,  Pageable pageable);

    boolean existsSeatBySeatNumberAndBus_Id(String seatNumber, Integer busId);

    @Query("SELECT COUNT(t) > 0 FROM Ticket t WHERE t.seat.id = :seatId ")
    boolean hasRelatedTickets(@Param("seatId") Integer seatId);

}

