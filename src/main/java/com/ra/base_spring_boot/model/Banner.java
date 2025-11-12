package com.ra.base_spring_boot.model;

import com.ra.base_spring_boot.model.base.BaseObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "banners")
public class Banner extends BaseObject {

    @Column(name = "banner_url")
    private String bannerUrl;

    @Column(length = 100)
    private String position;
}
