package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findByTicketId(Integer ticketId);

    @Query(value = """
     SELECT
         pp.provider_name as provider_name,
         count(p.id) as total_transactions,
         sum(p.amount) as total_amount
     from payment_providers pp
    join payments p on p.payment_provider_id = pp.id
     group by pp.id,pp.provider_name
    """,nativeQuery = true)
    List<Object[]> statisticByPaymentProvider();
}