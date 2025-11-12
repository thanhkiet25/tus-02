package com.ra.base_spring_boot.model;

import com.ra.base_spring_boot.model.base.BaseObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "bus_companies")
public class BusCompany extends BaseObject {
    @Column(name = "company_name")
    private String companyName;

    private String image;

    @Column(columnDefinition = "longtext")
    private String descriptions;

    @OneToMany(mappedBy = "busCompany")
    private Set<Bus> buses = new HashSet<>();
}
