package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.req.SeatRequest;
import com.ra.base_spring_boot.dto.resp.SeatListResponse;
import com.ra.base_spring_boot.dto.resp.SeatResponse;
import com.ra.base_spring_boot.model.BusCompany;
import com.ra.base_spring_boot.model.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISeatService {
    Page<SeatListResponse> findAllByBusCompany(int busCompanyId, Pageable pageable);

    SeatResponse createSeat(SeatRequest seatRequest);

    void deleteSeat( int seatId);
}
