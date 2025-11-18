package com.ra.base_spring_boot.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PopularBusCompanyDTO {
    private Integer bus_company_id;
    private String bus_company_name;
    private Long totalTickets;
}
