package com.ra.base_spring_boot.services;


import com.ra.base_spring_boot.dto.req.BusCreateRequest;
import com.ra.base_spring_boot.dto.resp.BusResponse;
import com.ra.base_spring_boot.dto.resp.BusListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBusService {
    //phương thức thêm bus
    BusResponse save(BusCreateRequest busReqDto);
    //phương thúc sửa bus
    BusResponse update(BusCreateRequest busCreateRequest, int id);
    //phương thức xóa bus
    String delete(int id);

    Page<BusListResponse> findAllByPage(Pageable pageable, String search);

}
