package com.ra.base_spring_boot.model;

import com.ra.base_spring_boot.model.base.BaseObject;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "cancellation_policies")
public class CancellationPolicy extends BaseObject {
    @Column(columnDefinition = "text")
    private String descriptions;

    @Column(name = "cancellation_time_limit")
    private Integer cancellationTimeLimit;

    @Column(name = "refunc_percentage")
    private Integer refundPercentage;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
}
