package com.ra.base_spring_boot.repository;
import com.ra.base_spring_boot.model.Bus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
    public interface IBusRepository extends JpaRepository<Bus,Integer> {

        @Query("select b from Bus b where b.busName like concat('%' , :search , '%') ")
        Page<Bus> findAllAndSearch(Pageable pageable , String search);

        boolean existsBusByBusName(String busName);

        boolean existsBusByBusNameAndIdNot(String busName, Integer busId);

         boolean existsByBusName(String busName);

         boolean existsByBusNameAndIdNot(String busName, Integer id);

    }



