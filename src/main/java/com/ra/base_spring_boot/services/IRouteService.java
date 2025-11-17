package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.ListRouteResp;
import com.ra.base_spring_boot.dto.resp.RouteRespDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRouteService {
 Page<ListRouteResp> getAllRoute(Pageable pageable);

 RouteRespDto findById(int id);

}
