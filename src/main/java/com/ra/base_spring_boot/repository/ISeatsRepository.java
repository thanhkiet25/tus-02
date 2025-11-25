package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISeatsRepository extends JpaRepository<Seat , Integer> {

    Seat findSeatById(int id);
}
