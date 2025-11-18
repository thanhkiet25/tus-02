package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IStationRepository extends JpaRepository<Station,Integer> {
    @Query("select s from Station s where s.name like concat('%' , :search , '%') ")
    Page<Station> findAllAndSearch(Pageable pageable , String search);

    Station findStationById(int id);

}