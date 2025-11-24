package com.ra.base_spring_boot.dto.req;

import com.ra.base_spring_boot.model.BusCompany;
import com.ra.base_spring_boot.model.Station;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BusCreateRequest {
    @NotNull(message = "vui long khong de trong")
    private String name;
    @NotNull(message = "vui long khong de trong")
    private String descriptions;
    @NotNull(message = "vui long khong de trong")
    private String licensePlate;
    @NotNull(message = "vui long khong de trong")
    private Integer capacity;
    @NotNull(message = "vui long khong de trong")
    private Set<MultipartFile> busImage;
    private int busCompanyId;
}
