package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.resp.RevenueDetailResponse;
import com.ra.base_spring_boot.dto.resp.RevenueStatisticsResponse;
import com.ra.base_spring_boot.dto.resp.StatisticsLoyalCustomerResponse;
import com.ra.base_spring_boot.dto.resp.StatisticsNewCustomerResponse;
import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.BusCompany;
import com.ra.base_spring_boot.model.Ticket;
import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.repository.IBusCompanyRepository;
import com.ra.base_spring_boot.repository.IRouteRepository;
import com.ra.base_spring_boot.repository.ITicketRepository;
import com.ra.base_spring_boot.repository.IUserRepository;
import com.ra.base_spring_boot.services.IStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements IStatisticsService {


    private final ITicketRepository ticketRepository;

    private final IBusCompanyRepository busCompanyRepository;

    private final IRouteRepository routeRepository;

    private final IUserRepository userRepository;

    @Override
    public RevenueStatisticsResponse getRevenueStatisticsByRouteAndBusCompanyForMonth(Integer routeId, String busCompanyName, Integer month, Integer year) {
        List<Ticket> tickets = new ArrayList<>();
        Double totalRevenue = 0.0;
        List<RevenueDetailResponse> details = new ArrayList<>();
        String trimmedBusCompanyName = busCompanyName.trim();
        if (!busCompanyRepository.existsByCompanyName(trimmedBusCompanyName)) {
            throw new HttpNotFound("busCompanyName not found");
        }
        if (!routeRepository.existsById(routeId)) {
            throw new HttpNotFound("routeId not found");
        }
        if (!routeRepository.existsRouteInBusCompanyName(routeId, trimmedBusCompanyName)) {
            throw new HttpConflict("Bus company name not exists route ID");
        }

        if (month < 1 || month > 12) {
            throw new HttpConflict("month must be between 1 and 12");
        }
        BusCompany busCompany = busCompanyRepository.findByCompanyName(trimmedBusCompanyName);
        LocalDateTime createdAt = busCompany.getCreatedAt();
        int minYear = createdAt.getYear();
        int currentYear = java.time.Year.now().getValue();
        if (year < minYear || year > currentYear) {
            throw new HttpConflict("year must be between "+ minYear +" and " + currentYear);
        }
        tickets = ticketRepository.findTicketsByRouteAndBusCompanyByMonth(routeId, trimmedBusCompanyName, month, year);
        totalRevenue = ticketRepository.calculateTotalRevenueByMonthRange(routeId, trimmedBusCompanyName, month, year);


// Tính số lượng vé và chi tiết doanh thu
        int countTickets = tickets.size();
        for (
                Ticket ticket : tickets) {
            details.add(new RevenueDetailResponse(ticket.getSchedule().getRoute().getId(),
                    ticket.getSchedule().getBus().getBusCompany().getCompanyName(),
                    ticket.getSeatType().name(),
                    ticket.getPrice(), ticket.getPayment().getCreatedAt(), ticket.getPayment().getPaymentMethod()));
        }
        return new RevenueStatisticsResponse(totalRevenue != null ? totalRevenue : 0, countTickets, details);

    }

    public RevenueStatisticsResponse getRevenueStatisticsByRouteAndBusCompanyForYear(Integer routeId, String busCompanyName, Integer year) {
        List<Ticket> tickets = new ArrayList<>();
        Double totalRevenue = 0.0;
        List<RevenueDetailResponse> details = new ArrayList<>();
        String trimmedBusCompanyName = busCompanyName.trim();
        if (!busCompanyRepository.existsByCompanyName(trimmedBusCompanyName)) {
            throw new HttpNotFound("busCompanyName not found");
        }
        if (!routeRepository.existsById(routeId)) {
            throw new HttpNotFound("routeId not found");
        }
        if (!routeRepository.existsRouteInBusCompanyName(routeId, trimmedBusCompanyName)) {
            throw new HttpConflict("Bus company name not exists route ID");
        }
        BusCompany busCompany = busCompanyRepository.findByCompanyName(trimmedBusCompanyName);
        LocalDateTime createdAt = busCompany.getCreatedAt();
        int minYear = createdAt.getYear();
        int currentYear = java.time.Year.now().getValue();
        if (year < minYear || year > currentYear) {
            throw new HttpConflict("year must be between "+ minYear +" and " + currentYear);
        }
        tickets = ticketRepository.findTicketsByRouteAndBusCompanyByYear(routeId, trimmedBusCompanyName, year);
        totalRevenue = ticketRepository.calculateTotalRevenueByYearRange(routeId, trimmedBusCompanyName, year);

// Tính số lượng vé và chi tiết doanh thu
        int countTickets = tickets.size();
        for (
                Ticket ticket : tickets) {
            details.add(new RevenueDetailResponse(ticket.getSchedule().getRoute().getId(),
                    ticket.getSchedule().getBus().getBusCompany().getCompanyName(),
                    ticket.getSeatType().name(),
                    ticket.getPrice(), ticket.getPayment().getCreatedAt(), ticket.getPayment().getPaymentMethod()));
        }
        return new RevenueStatisticsResponse(totalRevenue != null ? totalRevenue : 0, countTickets, details);

    }


    @Override
    public RevenueStatisticsResponse getRevenueStatisticsForDate(Integer routeId, String busCompanyName, LocalDate startDate, LocalDate endDate) {
        String trimmedBusCompanyName = busCompanyName.trim();
        if (!busCompanyRepository.existsByCompanyName(trimmedBusCompanyName)) {
            throw new HttpNotFound("busCompanyName not found");
        }
        if (!routeRepository.existsById(routeId)) {
            throw new HttpNotFound("routeId not found ");
        }
        if(!routeRepository.existsRouteInBusCompanyName(routeId, trimmedBusCompanyName)) {
            throw new HttpConflict("Bus company name not exists route ID");
        }
        if(startDate.isAfter(endDate)) {
            throw new HttpConflict("start date must be before end date");
        }
        LocalDate today = LocalDate.now();

        if (endDate.isAfter(today)) {
            throw new HttpConflict("endDate cannot be after today");
        }

        // Convert LocalDate → LocalDateTime
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        List<Ticket> tickets = ticketRepository.findTicketsByRouteAndBusCompanyByDate(
                routeId, busCompanyName, startDateTime, endDateTime);
        Double totalRevenue = ticketRepository.calculateTotalRevenueByDateRange(routeId, trimmedBusCompanyName, startDateTime, endDateTime);
        List<RevenueDetailResponse> details = new ArrayList<>();
        int countTickets = tickets.size();
        for (
                Ticket ticket : tickets) {
            details.add(new RevenueDetailResponse(ticket.getSchedule().getRoute().getId(),
                    ticket.getSchedule().getBus().getBusCompany().getCompanyName(),
                    ticket.getSeatType().name(),
                    ticket.getPrice(), ticket.getPayment().getCreatedAt(), ticket.getPayment().getPaymentMethod()));
        }

        return new RevenueStatisticsResponse(totalRevenue != null ? totalRevenue : 0, countTickets, details);
    }


    public StatisticsNewCustomerResponse getNewCustomerStatistics() {

        long newCustomerCount = userRepository.countNewCustomers();
        // Khách hàng mới
        List<Object[]> newCustomersData = userRepository.findNewCustomers();
        List<StatisticsNewCustomerResponse.CustomerInfoDTO> newCustomers = newCustomersData.stream().map(obj -> {
            User user = (User) obj[0];
            long totalTickets = (Long) obj[1];
            return new StatisticsNewCustomerResponse.CustomerInfoDTO(user.getEmail(),totalTickets);
        }).collect(Collectors.toList());

        return new StatisticsNewCustomerResponse(newCustomerCount,newCustomers);
    }

    public StatisticsLoyalCustomerResponse getLoyalCustomerStatistics() {
        Long loyalCustomerCount = userRepository.countLoyalCustomers();
        List<Object[]> loyalCustomersData = userRepository.findLoyalCustomers();
        List<StatisticsLoyalCustomerResponse.CustomerInfoDTO> loyalCustomers = loyalCustomersData.stream().map(objects -> {
            User user = (User) objects[0];
            long totalTickets = (Long) objects[1];
            return new StatisticsLoyalCustomerResponse.CustomerInfoDTO(user.getEmail(),totalTickets);
        }).collect(Collectors.toList());

        return new StatisticsLoyalCustomerResponse(loyalCustomerCount,loyalCustomers);
    }

}


