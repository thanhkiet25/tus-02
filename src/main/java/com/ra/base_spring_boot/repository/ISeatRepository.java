package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.BusCompany;
import com.ra.base_spring_boot.model.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISeatRepository extends JpaRepository<Seat,Integer> {
    Page<Seat> findAllByBus_BusCompany_Id(Integer busCompanyId, Pageable pageable);

    boolean existsSeatBySeatNumberAndBus_Id(String seatNumber, Integer busId);
}

