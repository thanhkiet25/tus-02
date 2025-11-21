package com.ra.base_spring_boot.services;
import com.ra.base_spring_boot.dto.resp.PopularBusCompanyResponse;
import com.ra.base_spring_boot.dto.req.BusCompanyRequest;
import com.ra.base_spring_boot.dto.resp.BusCompanyResponse;
import com.ra.base_spring_boot.model.BusCompany;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBusCompanyService {
    List<PopularBusCompanyResponse> getPopularBusCompanyDTO(int limit);

    Page<BusCompanyResponse> findBusCompanyByNameAndGetAllAndSort(String search, int page, int size, String sortBy, String direction);

    BusCompany createBusCompany(BusCompanyRequest busCompanyRequest);

    BusCompany findBusCompanyById(int id);

    BusCompany updateBusCompany(int id,BusCompanyRequest busCompanyRequest);

    void deleteBusCompany(int id);

}
