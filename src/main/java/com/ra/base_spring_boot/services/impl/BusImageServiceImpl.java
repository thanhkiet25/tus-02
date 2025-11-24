package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.model.BusImage;
import com.ra.base_spring_boot.repository.IBusImageRepository;
import com.ra.base_spring_boot.services.IBusImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BusImageServiceImpl implements IBusImageService {
    @Autowired
    private IBusImageRepository iBusImageRepository;

    @Override
    public Set<BusImage> findBusImageByStationId(List<Integer> id) {
        Set<BusImage> list = new HashSet<>();
        for(Integer i : id){
            list.add(iBusImageRepository.findBusImageById(i));
        }
        return list;
    }
}
