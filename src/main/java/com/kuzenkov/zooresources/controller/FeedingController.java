package com.kuzenkov.zooresources.controller;

import com.kuzenkov.zooresources.api.model.FeedingBatch;
import com.kuzenkov.zooresources.api.model.FeedingCreate;
import com.kuzenkov.zooresources.api.model.FeedingList;
import com.kuzenkov.zooresources.service.feeding.FeedingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feeding")
public class FeedingController {

    private final FeedingService feedingService;

    @PostMapping
    public Long addFeeding(@Valid @RequestBody FeedingCreate feedingCreate) {
        return feedingService.addFeeding(feedingCreate);
    }

    @PostMapping("/batch-update")
    public void batchUpdate(@Valid @RequestBody FeedingBatch feedingBatch) {
        feedingService.addFeedingBatch(feedingBatch);
    }

    @PostMapping("/clone-schedule")
    public void cloneSchedule(@Valid @RequestParam(value = "days", required = true) Integer days) {
        feedingService.cloneSchedule(days);
    }

    @GetMapping("/list")
    public FeedingList getFeedingList(@Valid @RequestParam(value = "days", required = true) Integer days){
        return feedingService.getFeedingList(days);
    }
}
