package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ITicketRepository extends JpaRepository<Ticket,Integer> {
    @Query("""
    SELECT t FROM Ticket t 
    JOIN t.payment p 
    JOIN p.user u WHERE t.id = :ticketId AND u.phone = :phone""")
    Optional<Ticket> searchTicket(@Param("ticketId") Integer ticketId, @Param("phone") String phone);




}
