package com.kuzenkov.zooresources.service.feeding;

import com.kuzenkov.zooresources.api.model.FeedingBatch;
import com.kuzenkov.zooresources.api.model.FeedingCreate;
import com.kuzenkov.zooresources.api.model.FeedingList;

public interface FeedingService {

    Long addFeeding(FeedingCreate feedingCreate);

    void addFeedingBatch(FeedingBatch feedingBatch);

    void cloneSchedule(Integer days);

    FeedingList getFeedingList(Integer days);
}
