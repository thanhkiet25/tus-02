package com.ra.base_spring_boot.services;
import com.ra.base_spring_boot.dto.req.BusCompanyUpdateRequest;
import com.ra.base_spring_boot.dto.resp.PopularBusCompanyResponse;
import com.ra.base_spring_boot.dto.req.BusCompanyCreateRequest;
import com.ra.base_spring_boot.dto.resp.BusCompanyResponse;
import com.ra.base_spring_boot.dto.resp.StatisticBusCompany;
import com.ra.base_spring_boot.model.BusCompany;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBusCompanyService {
    List<PopularBusCompanyResponse> getPopularBusCompanyDTO(int limit);

    Page<BusCompanyResponse> findBusCompanyByNameAndGetAllAndSort(String search, int page, int size, String sortBy, String direction);

    BusCompanyResponse getById(Integer id);

    BusCompanyResponse createBusCompany(BusCompanyCreateRequest busCompanyRequest);

    BusCompanyResponse updateBusCompany(int id, BusCompanyUpdateRequest busCompanyRequest);

    BusCompany findBusCompanyById(int id);

    BusCompanyResponse deleteBusCompany(int id);

    Page<StatisticBusCompany> getStatisticBusCompany(Pageable pageable);
}
