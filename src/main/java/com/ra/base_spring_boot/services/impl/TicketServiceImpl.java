package com.ra.base_spring_boot.services.impl;
import com.ra.base_spring_boot.dto.resp.StatisticTicketActiveResponse;
import com.ra.base_spring_boot.dto.resp.TicketResponse;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.Ticket;
import com.ra.base_spring_boot.model.constants.TicketStatus;
import com.ra.base_spring_boot.repository.ITicketRepository;
import com.ra.base_spring_boot.services.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ra.base_spring_boot.services.IRouteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements ITicketService {
    private final ITicketRepository ticketRepository;
    private final IRouteService routeService;
    @Override
    public TicketResponse searchTicket(Integer id, String phone) {
        Ticket ticket = ticketRepository.searchTicket(id, phone)
                .orElseThrow(() -> new HttpNotFound("Ticket not found."));

        return TicketResponse.builder()
                .id(ticket.getId())
                .departureTime(ticket.getDepartureTime())
                .arrivalTime(ticket.getArrivalTime())
                .seatType(ticket.getSeatType())
                .price(ticket.getPrice())
                .status(ticket.getStatus())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }
  @Override
  public Page<TicketResponse> getAllAndSearchTicket( String keyword, Pageable pageable){
      if (keyword == null || keyword.trim().isEmpty()) {
          keyword = null;
      }else {
          keyword = keyword.trim().toLowerCase();
      }
      return   ticketRepository.getAllAndSearchTicket(keyword, pageable)
                .map(TicketResponse::new);
    }
    @Override
    public Page<TicketResponse> getTicketByCompany( Integer companyId, Pageable pageable) {
        return   ticketRepository.getTicketByCompany(companyId, pageable)
                .map(TicketResponse::new);
    }
    @Override
    public Page<TicketResponse> getTicketByStatus (TicketStatus status, Pageable pageable) {
        return ticketRepository.getTicketByStatus(status, pageable)
                .map(TicketResponse::new);
    }




    @Override
    public StatisticTicketActiveResponse getTicketActive() {
       Object result = ticketRepository.getStatisticTicketActive();
       Object[] row = (Object[]) result;
       Integer booked = ((Number) row[0]).intValue();
       Integer cancelled = ((Number) row[1]).intValue();
       return new StatisticTicketActiveResponse(booked, cancelled);
    }

}
