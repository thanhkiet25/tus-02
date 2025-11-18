package com.ra.base_spring_boot.dto.resp;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BannerDTO {
    private Integer id;
    private String bannerUrl;
    private String position;
}
