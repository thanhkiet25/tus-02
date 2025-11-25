package com.ra.base_spring_boot.services;
import com.ra.base_spring_boot.dto.req.BusCompanyUpdateRequest;
import com.ra.base_spring_boot.dto.resp.PopularBusCompanyResponse;
import com.ra.base_spring_boot.dto.req.BusCompanyCreateRequest;
import com.ra.base_spring_boot.dto.resp.BusCompanyResponse;
import com.ra.base_spring_boot.model.BusCompany;
import org.springframework.data.domain.Page;

import com.ra.base_spring_boot.dto.resp.PopularBusCompanyResponse;
import com.ra.base_spring_boot.model.BusCompany;

import java.util.List;

public interface IBusCompanyService {
    List<PopularBusCompanyResponse> getPopularBusCompanyDTO(int limit);

    Page<BusCompanyResponse> findBusCompanyByNameAndGetAllAndSort(String search, int page, int size, String sortBy, String direction);

    BusCompany findById(Integer id);

    BusCompanyResponse createBusCompany(BusCompanyCreateRequest busCompanyRequest);

    BusCompanyResponse updateBusCompany(int id, BusCompanyUpdateRequest busCompanyRequest);

    BusCompanyResponse deleteBusCompany(int id);
}
