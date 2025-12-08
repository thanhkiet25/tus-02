package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.BookingRequest;
import com.ra.base_spring_boot.dto.resp.BookingHistoryResponse;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.*;
import com.ra.base_spring_boot.model.constants.PaymentMethod;
import com.ra.base_spring_boot.model.constants.PaymentStatus;
import com.ra.base_spring_boot.model.constants.TicketStatus;
import com.ra.base_spring_boot.repository.*;
import com.ra.base_spring_boot.security.principle.MyUserDetails;
import com.ra.base_spring_boot.services.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingService {

    private final ITicketRepository ticketRepository;
    private final IScheduleRepository scheduleRepository;
    private final ISeatRepository seatRepository;
    private final IPaymentProviderRepository paymentProviderRepository;
    private final IPaymentRepository paymentRepository;
    private final IUserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<BookingHistoryResponse> bookTicket(BookingRequest request) {

        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new HttpNotFound("User not found"));

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new HttpNotFound("Schedule not found"));

        if (schedule.getAvailableSeat() < request.getSeatIds().size()) {
            throw new HttpConflict("Not enough seats available");
        }

        PaymentProvider paymentProvider = paymentProviderRepository.findById(request.getPaymentProviderId())
                .orElseThrow(() -> new HttpNotFound("Payment provider not found"));

        List<BookingHistoryResponse> responseList = new ArrayList<>();

        for (Integer seatId : request.getSeatIds()) {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new HttpNotFound("Seat not found ID: " + seatId));

            if (!seat.getBus().getId().equals(schedule.getBus().getId())) {
                throw new HttpBadRequest("Seat " + seat.getSeatNumber() + " does not belong to this bus.");
            }

            if (ticketRepository.existsByScheduleAndSeat(schedule.getId(), seatId)) {
                throw new HttpConflict("Seat " + seat.getSeatNumber() + " is already booked.");
            }

            Double ticketPrice = schedule.getRoute().getPrice() + seat.getPriceForSeatType();

            Ticket ticket = new Ticket();
            ticket.setSchedule(schedule);
            ticket.setSeat(seat);
            ticket.setSeatType(seat.getSeatType());
            ticket.setPrice(ticketPrice);
            ticket.setDepartureTime(schedule.getDepartureTime());
            ticket.setArrivalTime(schedule.getArrivalTime());
            ticket.setStatus(TicketStatus.BOOKED);

            Ticket savedTicket = ticketRepository.save(ticket);

            Payment payment = new Payment();
            payment.setUser(user);
            payment.setTicket(savedTicket);
            payment.setPaymentProvider(paymentProvider);
            payment.setPaymentMethod(PaymentMethod.ONLINE);
            payment.setAmount(ticketPrice);
            payment.setStatus(PaymentStatus.PENDING);

            paymentRepository.save(payment);
            savedTicket.setPayment(payment);

            responseList.add(new BookingHistoryResponse(savedTicket));
        }

        schedule.setAvailableSeat(schedule.getAvailableSeat() - request.getSeatIds().size());
        scheduleRepository.save(schedule);

        return responseList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookingHistoryResponse cancelTicket(Integer ticketId) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new HttpNotFound("Ticket not found"));

        if (!ticket.getPayment().getUser().getId().equals(user.getId())) {
            throw new HttpBadRequest("You can only cancel your own tickets");
        }

        if (ticket.getStatus() == TicketStatus.CANCELLED) {
            throw new HttpBadRequest("Ticket is already cancelled");
        }

        Payment payment = ticket.getPayment();
        if (payment != null) {
            if (payment.getStatus() == PaymentStatus.COMPLETED) {
                payment.setStatus(PaymentStatus.REFUND_NEEDED);
            } else if (payment.getStatus() == PaymentStatus.PENDING) {
                payment.setStatus(PaymentStatus.FAILED);
            }
            paymentRepository.save(payment);
        }

        ticket.setStatus(TicketStatus.CANCELLED);

        Ticket savedTicket = ticketRepository.save(ticket);

        scheduleRepository.incrementAvailableSeat(ticket.getSchedule().getId());

        return new BookingHistoryResponse(savedTicket);}

    @Override
    public Page<BookingHistoryResponse> getHistory(TicketStatus status, String companyName, Pageable pageable) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        if (companyName != null) {
            companyName = companyName.trim();
        }
        return ticketRepository.findHistory(user.getId(), status, companyName, pageable);
    }
}