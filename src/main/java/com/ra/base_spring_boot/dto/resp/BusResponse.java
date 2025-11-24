package com.ra.base_spring_boot.dto.resp;

import com.ra.base_spring_boot.model.Bus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BusResponse {
    private String name;
    private String descriptions;
    private String licensePlate;
    private Integer capacity;
    private String busImage;
    private LocalDateTime createdAt;
    private String busCompanyName;

}
