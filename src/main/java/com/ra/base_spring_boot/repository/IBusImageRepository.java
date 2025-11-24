package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.BusImage;
import org.springframework.data.repository.CrudRepository;

public interface IBusImageRepository extends CrudRepository<BusImage, Integer> {
    BusImage findBusImageById(Integer id);
}
