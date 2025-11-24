package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.BusCreateRequest;
import com.ra.base_spring_boot.services.IBusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/admin/bus")
public class AminBusController {
    @Autowired
    private IBusService busService;

    @GetMapping
    public ResponseEntity<?> findAllAndSearchBus(@RequestParam(defaultValue = "") String search,
                                                 @PageableDefault(page = 0, size = 10, sort = "createdAt" , direction = Sort.Direction.DESC)Pageable pageable){
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(busService.findAllByPage(pageable, search))
                .build(), HttpStatus.OK);
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createBus(@Valid @ModelAttribute BusCreateRequest busCreateRequest){
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(busService.save(busCreateRequest))
                .build(),HttpStatus.OK);
    }
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateBus(@Valid @ModelAttribute BusCreateRequest busCreateRequest, @PathVariable int id){
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .data(busService.update(busCreateRequest, id))
                .build(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBus(@PathVariable int id){
        return new ResponseEntity<>(ResponseWrapper.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(busService.delete(id))
                .build(),HttpStatus.OK);
    }
}
