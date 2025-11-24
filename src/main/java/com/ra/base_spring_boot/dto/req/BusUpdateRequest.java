package com.ra.base_spring_boot.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BusUpdateRequest {
    @NotNull(message = "vui long khong de trong")
    private String descriptions;
    @NotNull(message = "vui long khong de trong")
    private String licensePlate;
    @NotNull(message = "vui long khong de trong")
    private Integer capacity;
    @NotNull(message = "vui long khong de trong")
    private int busCompany;
    @NotNull(message = "vui long khong de trong")
    private int stations;
    private String oldImage;
    private MultipartFile busImage;
    @NotNull(message = "vui long khong de trong")
    private int reviews;
    @NotNull(message = "vui long khong de trong")
    private int seats;
    @NotNull(message = "vui long khong de trong")
    private int schedules;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
