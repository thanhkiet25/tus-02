package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.PaymentProvider;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface IPaymentProviderRepository extends JpaRepository<PaymentProvider,Integer> {

    Page<PaymentProvider> findAllPaymentProviderByProviderNameContainingIgnoreCase(String searchProviderName, Pageable pageable);

    boolean existsPaymentProviderByProviderName(String providerName);

    @Query("SELECT COUNT(p) > 0 FROM Payment p WHERE p.paymentProvider.id = :paymentProviderId ")
    boolean hasRelatedPayment(@Param("paymentProviderId") Integer paymentProviderId);

}
