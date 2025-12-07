package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.BannerRequest;
import com.ra.base_spring_boot.dto.resp.BannerResponse;
import com.ra.base_spring_boot.services.impl.BannerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/banners")
@RequiredArgsConstructor
public class AdminBannerController {
    private final BannerServiceImpl bannerService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<BannerResponse> banner = bannerService.getAllBanner(keyword, pageable);
        return new ResponseEntity<>(ResponseWrapper
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(banner)
                .build(),
                HttpStatus.OK);
    }
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createBanner(@Valid @ModelAttribute BannerRequest req) {
        return new ResponseEntity<>(ResponseWrapper
                .builder()
                .status(HttpStatus.CREATED)
                .code(HttpStatus.CREATED.value())
                .data(bannerService.createBanner(req))
                .build(),
                HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBanner(@PathVariable Integer id) {
        bannerService.deleteBanner(id);
        return new ResponseEntity<>(ResponseWrapper
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data("Deleted Successfully")
                .build(),
                HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<?>> getBannerById(@PathVariable Integer id) {
        BannerResponse bannerRes = bannerService.getBannerById(id);
        return new ResponseEntity<>(ResponseWrapper
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(bannerRes)
                .build(),
                HttpStatus.OK);

    }
}
