package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.req.CancellationPolicyRequest;
import com.ra.base_spring_boot.dto.resp.CancellationPolicyListResponse;
import com.ra.base_spring_boot.dto.resp.CancellationPolicyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICancellationPolicyService {
    Page<CancellationPolicyListResponse> getAllCancellationPolicyAndSearchByRoute (String searchStation, Pageable pageable);

    CancellationPolicyResponse findById(Integer id);

    CancellationPolicyResponse createCancellationPolicy(CancellationPolicyRequest cancellationPolicyRequest);

    void deleteCancellationPolicy(Integer id);

}
