package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.model.BusImage;

import java.util.List;
import java.util.Set;

public interface IBusImageService {
    Set<BusImage> findBusImageByStationId(List<Integer> id);
}
