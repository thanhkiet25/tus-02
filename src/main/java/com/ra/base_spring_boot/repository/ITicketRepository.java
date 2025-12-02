package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.dto.resp.BookingHistoryResponse;
import com.ra.base_spring_boot.model.Ticket;
import com.ra.base_spring_boot.model.constants.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ITicketRepository extends JpaRepository<Ticket, Integer> {
    @Query("""
    SELECT t FROM Ticket t 
    JOIN t.payment p 
    JOIN p.user u WHERE t.id = :ticketId AND u.phone = :phone""")
    Optional<Ticket> searchTicket(@Param("ticketId") Integer id, @Param("phone") String phone);

    @Query("""
    SELECT t FROM Ticket t
    WHERE (:keyword IS NULL 
        
           OR LOWER(t.schedule.route.departureStation.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(t.schedule.route.arrivalStation.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(t.seat.seatNumber) LIKE LOWER(CONCAT('%', :keyword, '%')))
    """)
    Page<Ticket> getAllAndSearchTicket(@Param("keyword") String keyword, Pageable pageable);

    @Query("""
    SELECT t FROM Ticket t
    WHERE t.schedule.bus.busCompany.id = :companyId
    """)
    Page<Ticket> getTicketByCompany(@Param("companyId") Integer companyId, Pageable pageable);

    Page<Ticket> getTicketByStatus(TicketStatus status, Pageable pageable);


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
// lấy danh sách vé theo tuần
    @Query("SELECT t FROM Ticket t WHERE t.schedule.route.id = :routeId " +
            " AND t.schedule.bus.busCompany.companyName like :busCompanyName " +
            "AND t.status = 'BOOKED' AND t.payment.status = 'COMPLETED' " +
            "AND  t.payment.createdAt >= :startDate AND t.payment.createdAt <= :endDate")
    List<Ticket> findTicketsByRouteAndBusCompanyByWeek(@Param("routeId") Integer routeId,
                                                       @Param("busCompanyName") String busCompanyName,
                                                       @Param("startDate") LocalDateTime startDate,
                                                       @Param("endDate") LocalDateTime endDate);
// lấy danh sách vé theo tháng
    @Query("SELECT t FROM Ticket t WHERE t.schedule.route.id = :routeId " +
            " AND t.schedule.bus.busCompany.companyName like :busCompanyName " +
            "AND t.status = 'BOOKED' AND t.payment.status = 'COMPLETED' " +
            "AND  MONTH(t.payment.createdAt ) = :month AND YEAR(t.payment.createdAt) = :year")
    List<Ticket> findTicketsByRouteAndBusCompanyByMonth(@Param("routeId") Integer routeId,
                                                        @Param("busCompanyName") String busCompanyName,
                                                        @Param("month") int month,
                                                        @Param("year") int year);

// lấy danh sách vé theo năm
    @Query("SELECT t FROM Ticket t WHERE t.schedule.route.id = :routeId " +
            " AND t.schedule.bus.busCompany.companyName like :busCompanyName " +
            "AND t.status = 'BOOKED' AND t.payment.status = 'COMPLETED' " +
            "AND YEAR(t.payment.createdAt) = :year")
    List<Ticket> findTicketsByRouteAndBusCompanyByYear(@Param("routeId") Integer routeId,
                                                       @Param("busCompanyName") String busCompanyName,
                                                       @Param("year") int year);

    //tính tổng tiền theo tuần
    @Query("SELECT SUM(t.price) FROM Ticket t WHERE t.schedule.route.id = :routeId " +
            " AND t.schedule.bus.busCompany.companyName like :busCompanyName " +
            "AND t.status = 'BOOKED' AND t.payment.status = 'COMPLETED' " +
            "AND  t.payment.createdAt >= :startDate AND t.payment.createdAt <= :endDate")
    Double calculateTotalRevenueByDateRange(@Param("routeId") Integer routeId,
                                            @Param("busCompanyName") String busCompanyName,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    // tính tổng tiền theo tháng
    @Query("SELECT SUM(t.price) FROM Ticket t WHERE t.schedule.route.id = :routeId " +
            " AND t.schedule.bus.busCompany.companyName like :busCompanyName " +
            "AND t.status = 'BOOKED' AND t.payment.status = 'COMPLETED' " +
            "AND  MONTH(t.payment.createdAt ) = :month AND YEAR(t.payment.createdAt) = :year")
    Double calculateTotalRevenueByMonthRange(@Param("routeId") Integer routeId,
                                             @Param("busCompanyName") String busCompanyName,
                                             @Param("month") int month,
                                             @Param("year") int year);

    // tính tổng tiền theo năm
    @Query("SELECT SUM(t.price) FROM Ticket t WHERE t.schedule.route.id = :routeId " +
            " AND t.schedule.bus.busCompany.companyName like :busCompanyName " +
            "AND t.status = 'BOOKED' AND t.payment.status = 'COMPLETED' " +
            " AND YEAR(t.payment.createdAt) = :year")
    Double calculateTotalRevenueByYearRange(@Param("routeId") Integer routeId,
                                            @Param("busCompanyName") String busCompanyName,
                                            @Param("year") int year);


    @Query(value = """
     select
      SUM(CASE WHEN t.status = 'BOOKED' THEN 1 ELSE 0 END ) AS booked_count,
      SUM(CASE WHEN t.status = 'CANCELLED' THEN 1 ELSE 0 END ) AS cancelled_count
      FROM tickets t
    """,nativeQuery = true)
    Object getStatisticTicketActive();
}
