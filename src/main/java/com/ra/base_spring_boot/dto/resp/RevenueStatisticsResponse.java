package com.ra.base_spring_boot.dto.resp;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RevenueStatisticsResponse {
    private double totalRevenue;
    private int countTickets;
    private List<RevenueDetailResponse> details;

}
