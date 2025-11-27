package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.CancellationPolicyRequest;
import com.ra.base_spring_boot.dto.resp.CancellationPolicyListResponse;
import com.ra.base_spring_boot.dto.resp.CancellationPolicyResponse;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.CancellationPolicy;
import com.ra.base_spring_boot.model.Route;
import com.ra.base_spring_boot.repository.ICancellationPolicyRepository;
import com.ra.base_spring_boot.repository.IRouteRepository;
import com.ra.base_spring_boot.services.ICancellationPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancellationPolicyServiceImpl implements ICancellationPolicyService {

    private final ICancellationPolicyRepository cancellationPolicyRepository;

    private final IRouteRepository routeRepository;

    @Override
    public Page<CancellationPolicyListResponse> getAllCancellationPolicyAndSearchByRoute(String search, Pageable pageable) {
        String trimmedSearch = (search != null) ? search.trim() : "";
        Page<CancellationPolicy> list = cancellationPolicyRepository.getAllCancellationPolicyAndSearchByRoute(trimmedSearch, pageable);
        if (list.isEmpty()) {
            throw new HttpNotFound("List cancellation policy with search is ["+search+"] CancellationPolicy is empty");
        }
        return list.map(CancellationPolicyListResponse::new);
    }

    @Override
    public CancellationPolicyResponse findById(Integer id) {
        CancellationPolicy cancellationPolicy = cancellationPolicyRepository.findById(id).orElseThrow(() -> new HttpNotFound("CancellationPolicy id " + id + " is not found"));
        return new CancellationPolicyResponse(cancellationPolicy);

    }

    @Override
    public CancellationPolicyResponse createCancellationPolicy(CancellationPolicyRequest cancellationPolicyRequest) {
        Route route = routeRepository.findById(cancellationPolicyRequest.getRouteId()).orElseThrow(() -> new HttpNotFound("Route id " + cancellationPolicyRequest.getRouteId() + " is not found"));
        CancellationPolicy cancellationPolicy = new CancellationPolicy();
        cancellationPolicy.setRoute(route);
        cancellationPolicy.setDescriptions(cancellationPolicyRequest.getDescriptions());
        cancellationPolicy.setCancellationTimeLimit(cancellationPolicyRequest.getCancellationTimeLimit());
        cancellationPolicy.setRefundPercentage(cancellationPolicyRequest.getRefundPercentage());
        CancellationPolicy newCancellation = cancellationPolicyRepository.save(cancellationPolicy);
        return new CancellationPolicyResponse(newCancellation);
    }


    @Override
    public void deleteCancellationPolicy(Integer id) {
        CancellationPolicy cancellationPolicy = cancellationPolicyRepository.findById(id).orElseThrow(() -> new HttpNotFound("CancellationPolicy id " + id + " is not found"));
        cancellationPolicyRepository.delete(cancellationPolicy);
    }
}
