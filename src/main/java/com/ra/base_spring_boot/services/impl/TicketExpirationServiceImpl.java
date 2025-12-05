package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.model.Schedule;
import com.ra.base_spring_boot.model.Ticket;
import com.ra.base_spring_boot.model.constants.PaymentStatus;
import com.ra.base_spring_boot.model.constants.TicketStatus;
import com.ra.base_spring_boot.repository.IPaymentRepository;
import com.ra.base_spring_boot.repository.IScheduleRepository;
import com.ra.base_spring_boot.repository.ITicketRepository;
import com.ra.base_spring_boot.services.ITicketExpirationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketExpirationServiceImpl implements ITicketExpirationService {

    private final ITicketRepository ticketRepository;
    private final IScheduleRepository scheduleRepository;
    private final IPaymentRepository paymentRepository;

    @Override
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void scanAndCancelExpiredTickets() {
        LocalDateTime thresholdTime = LocalDateTime.now().minusMinutes(10);

        List<Ticket> expiredTickets = ticketRepository.findExpiredTickets(thresholdTime);

        if (!expiredTickets.isEmpty()) {
            log.info("Found {} expired tickets. Cancelling...", expiredTickets.size());

            for (Ticket ticket : expiredTickets) {

                ticket.setStatus(TicketStatus.CANCELLED);
                ticketRepository.save(ticket);

                if (ticket.getPayment() != null) {
                    ticket.getPayment().setStatus(PaymentStatus.FAILED);
                    paymentRepository.save(ticket.getPayment());
                }

                Schedule schedule = ticket.getSchedule();
                schedule.setAvailableSeat(schedule.getAvailableSeat() + 1);
                scheduleRepository.save(schedule);

                log.info("Cancelled Ticket ID: {}", ticket.getId());
            }
        }
    }
}
