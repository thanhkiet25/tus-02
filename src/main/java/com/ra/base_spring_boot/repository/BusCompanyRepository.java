package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.BusCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusCompanyRepository extends JpaRepository<BusCompany,Integer> {

}
