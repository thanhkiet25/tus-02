package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.PopularBusCompanyDTO;

import java.util.List;

public interface IBusCompanyService {
    List<PopularBusCompanyDTO> getPopularBusCompanyDTO(int limit);
}
