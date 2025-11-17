package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.Route;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface IRouteRepository extends JpaRepository<Route,Integer> {
    Page<Route> findAll(Pageable pageable);

    Route findById(int id);
}
