package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.SeatRequest;
import com.ra.base_spring_boot.dto.resp.SeatListResponse;
import com.ra.base_spring_boot.dto.resp.SeatResponse;
import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.Bus;
import com.ra.base_spring_boot.model.BusCompany;
import com.ra.base_spring_boot.model.Seat;
import com.ra.base_spring_boot.model.constants.SeatStatus;
import com.ra.base_spring_boot.repository.IBusCompanyRepository;
import com.ra.base_spring_boot.repository.IBusRepository;
import com.ra.base_spring_boot.repository.ISeatRepository;
import com.ra.base_spring_boot.services.ISeatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatServiceImpl implements ISeatService {

    private final ISeatRepository seatRepository;

    private final IBusCompanyRepository busCompanyRepository;

    private final IBusRepository busRepository;

    @Override
    public Page<SeatListResponse> findAllByBusCompany(int busCompanyId, String searchBusName, Pageable pageable) {
        String trimmedSearch = searchBusName.trim();
        BusCompany busCompany = busCompanyRepository.findById(busCompanyId).orElseThrow(() -> new HttpNotFound("Not found bus company id is " + busCompanyId));
        if (busCompany.getId() != null) {
            return seatRepository.findAllByBus_BusCompany_IdAndBus_BusNameContaining(busCompanyId, trimmedSearch, pageable).map(SeatListResponse::new);
        }
        return null;
    }
    @Override
    public SeatResponse createSeat(SeatRequest seatRequest) {
        if(seatRepository.existsSeatBySeatNumberAndBus_Id(seatRequest.getSeatNumber(),seatRequest.getBusId())){
            throw new HttpConflict("seat number and bus id already exist");
        }
        Seat seat = new Seat();
        seat.setSeatNumber(seatRequest.getSeatNumber());
        seat.setSeatType(seatRequest.getSeatType());
        seat.setStatus(seatRequest.getStatus());
        seat.setPriceForSeatType(seatRequest.getPriceForSeatType());
        Bus bus = busRepository.findById(seatRequest.getBusId()).orElseThrow(() -> new HttpNotFound("Not found bus id is" + seatRequest.getBusId()));
        seat.setBus(bus);
        Seat newSeat = seatRepository.save(seat);
        return new SeatResponse(newSeat);
    }

    @Override
    public void deleteSeat( int seatId) {
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new HttpNotFound("Not found seat id is" + seatId));
        if(seatRepository.hasRelatedTickets(seatId)){
            throw new HttpConflict ("Can not delete seat because seat has related tickets, please remove ticket.");
        }
       if(seat.getStatus() == SeatStatus.AVAILABLE){
           seatRepository.deleteById(seatId);
       }else {
           throw new HttpConflict("seat status is " + seat.getStatus() +" can not delete.");
       }
    }

    @Override
    public SeatResponse findSeatById(int seatId) {
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new HttpNotFound("Not found seat id is " + seatId));
        return new SeatResponse(seat);
    }
}
