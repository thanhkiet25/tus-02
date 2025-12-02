package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.ScheduleCreateRequest;
import com.ra.base_spring_boot.dto.req.ScheduleUpdateRequest;
import com.ra.base_spring_boot.dto.resp.ScheduleResponse;
import com.ra.base_spring_boot.model.Schedule;
import com.ra.base_spring_boot.services.IScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/Schedules")
public class AdminScheduleController {
   private final IScheduleService scheduleService;

   @GetMapping
    public ResponseEntity<ResponseWrapper<Page<ScheduleResponse>>> getScheduleAndSortAndSearch (
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "availableSeat") String sortBy,
            @RequestParam(defaultValue = "desc")  String direction
   ) {
       Page<ScheduleResponse> schedules = scheduleService.getScheduleAndSortAndSearch(search, page, size, sortBy, direction);
      return ResponseEntity.status(HttpStatus.OK).body(
             ResponseWrapper.<Page<ScheduleResponse>>builder()
                     .status(HttpStatus.OK)
                     .code(HttpStatus.OK.value())
                     .data(schedules)
                     .build()
      );
   }
   @GetMapping("findById/{id}")
   public ResponseEntity<?> getScheduleById(@PathVariable Integer id) {
      ScheduleResponse scheduleResponse = scheduleService.getScheduleById(id);
      return ResponseEntity.status(HttpStatus.OK).body(
              ResponseWrapper.<ScheduleResponse>builder()
              .status(HttpStatus.OK)
              .code(HttpStatus.OK.value())
              .data(scheduleResponse)
              .build()
      );
   }

   @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseWrapper<?>> createSchedule(@Valid @ModelAttribute ScheduleCreateRequest scheduleRequest) {
       ScheduleResponse schedule = scheduleService.createSchedule(scheduleRequest);
       return ResponseEntity.status(HttpStatus.CREATED).body(
               ResponseWrapper.builder()
                       .status(HttpStatus.CREATED)
                       .code(201)
                       .data(schedule)
                       .build()
       );
   }
   @PutMapping(value = "/update/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseWrapper<?>> updateSchedule( @PathVariable int id,@Valid  @ModelAttribute ScheduleUpdateRequest  scheduleRequest) {
       ScheduleResponse schedule = scheduleService.updateSchedule(id, scheduleRequest);
       return ResponseEntity.status(HttpStatus.OK).body(
               ResponseWrapper.builder()
                       .status(HttpStatus.OK)
                       .code(200)
                       .data(schedule)
                       .build()
       );
   }
   @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<?>> deleteSchedule(@PathVariable int id) {
       ScheduleResponse scheduleResponse = scheduleService.deleteSchedule(id);
       return ResponseEntity.status(HttpStatus.OK).body(
               ResponseWrapper.builder()
                       .status(HttpStatus.OK)
                       .code(200)
                       .data(scheduleResponse)
                       .build()
       );
   }
}
