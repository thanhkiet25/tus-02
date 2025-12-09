package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.CancellationPolicy;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ICancellationPolicyRepository extends JpaRepository<CancellationPolicy, Integer> {
    @Query("SELECT cp FROM CancellationPolicy cp " +
            "JOIN cp.route r " +
            "WHERE r.departureStation.name LIKE CONCAT('%', :searchStation, '%') " +
            "OR r.arrivalStation.name LIKE CONCAT('%', :searchStation, '%')")
    Page<CancellationPolicy> getAllCancellationPolicyAndSearchByRoute(@Param("searchStation") String searchStation,
                                                                      Pageable pageable);

}
