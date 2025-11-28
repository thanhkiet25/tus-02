package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.dto.resp.BookingHistoryResponse;
import com.ra.base_spring_boot.model.Ticket;
import com.ra.base_spring_boot.model.constants.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ITicketRepository extends JpaRepository<Ticket,Integer> {
    @Query("""
    SELECT t FROM Ticket t 
    JOIN t.payment p 
    JOIN p.user u WHERE t.id = :ticketId AND u.phone = :phone""")
    Optional<Ticket> searchTicket(@Param("ticketId") Integer ticketId, @Param("phone") String phone);


    // Đặt vé và lịch sử đặt vé (nhinty)
    // 1. Kiểm tra trùng vé
    @Query("SELECT COUNT(t) > 0 FROM Ticket t " +
            "WHERE t.schedule.id = :scheduleId " +
            "AND t.seat.id = :seatId " +
            "AND t.status = 'BOOKED'")
    boolean existsByScheduleAndSeat(@Param("scheduleId") Integer scheduleId,
                                    @Param("seatId") Integer seatId);

    // 2. Lấy danh sách ID ghế đã đặt
    @Query("SELECT t.seat.id FROM Ticket t WHERE t.schedule.id = :scheduleId AND t.status = 'BOOKED'")
    List<Integer> findBookedSeatIdsByScheduleId(Integer scheduleId);

    // 3. Lấy lịch sử vé (Lọc theo trạng thái vé hoặc tên nhà xe)
    @Query("""
        SELECT new com.ra.base_spring_boot.dto.resp.BookingHistoryResponse(
            t.id,
            CONCAT(r.departureStation.name, ' - ', r.arrivalStation.name),
            bc.companyName,
            b.licensePlate,
            s.seatNumber,
            sch.departureTime,
            t.price,
            t.status,
            p.status,
            t.createdAt
        )
        FROM Ticket t
        JOIN t.schedule sch
        JOIN sch.bus b
        JOIN b.busCompany bc
        JOIN sch.route r
        JOIN t.seat s
        JOIN t.payment p
        WHERE t.payment.user.id = :userId
        AND (:status IS NULL OR t.status = :status)
        AND (:companyName IS NULL OR bc.companyName LIKE CONCAT('%', :companyName, '%'))
        ORDER BY t.createdAt DESC
    """)
    List<BookingHistoryResponse> findHistory(
            @Param("userId") Integer userId,
            @Param("status") TicketStatus status,
            @Param("companyName") String companyName
    );
}
