package com.kuzenkov.zooresources.service.feeding;

import com.kuzenkov.zooresources.api.model.*;
import com.kuzenkov.zooresources.entity.AnimalEntity;
import com.kuzenkov.zooresources.entity.FeedingEntity;
import com.kuzenkov.zooresources.entity.FoodEntity;
import com.kuzenkov.zooresources.exception.AppNotFoundException;
import com.kuzenkov.zooresources.exception.AppValidationException;
import com.kuzenkov.zooresources.repository.AnimalRepository;
import com.kuzenkov.zooresources.repository.FeedingRepository;
import com.kuzenkov.zooresources.repository.FoodRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FeedingServiceImpl implements FeedingService {

    private final AnimalRepository animalRepository;
    private final FoodRepository foodRepository;
    private final FeedingRepository feedingRepository;

    public Long addFeeding(FeedingCreate feedingCreate) {
        if (Objects.isNull(feedingCreate.getAnimalId()) || Objects.isNull(feedingCreate.getFoodId())) {
            throw new AppValidationException("Отсутствует обязательный параметр: id животного/продукта");
        }
        FeedingEntity feedingEntity = new FeedingEntity();
        AnimalEntity animalEntity = animalRepository.findById(feedingCreate.getAnimalId())
                .orElseThrow(() -> new AppNotFoundException(String.format("Животное с id:%d не найдено",
                        feedingCreate.getAnimalId())));
        FoodEntity foodEntity = foodRepository.findById(feedingCreate.getFoodId())
                .orElseThrow(() -> new AppNotFoundException(String.format("Продукт с id:%d не найден",
                        feedingCreate.getFoodId())));
        feedingEntity.setAnimal(animalEntity);
        feedingEntity.setFood(foodEntity);
        feedingEntity.setFeedingTime(LocalDate.parse(feedingCreate.getFeedingTime()));
        feedingEntity.setQuantity(feedingCreate.getQuantity());
        return feedingRepository.save(feedingEntity).getId();
    }

    public void addFeedingBatch(FeedingBatch feedingBatch) {
        if (Objects.isNull(feedingBatch.getAnimalId()) || Objects.isNull(feedingBatch.getFoodInfo())) {
            throw new AppValidationException("Отсутствует обязательный параметр: id животного/информация о продукте");
        }
        AnimalEntity animalEntity = animalRepository.findById(feedingBatch.getAnimalId())
                .orElseThrow(() -> new AppNotFoundException(String.format("Животное с id:%d не найдено",
                        feedingBatch.getAnimalId())));
        List<FeedingItems> items = feedingBatch.getFoodInfo();
        items.forEach(item -> {
            FeedingEntity feedingEntity = new FeedingEntity();
            FoodEntity foodEntity = foodRepository.findById(item.getFoodId())
                    .orElseThrow(() -> new AppNotFoundException(String.format("Продукт с id:%d не найден",
                            item.getFoodId())));
            feedingEntity.setAnimal(animalEntity);
            feedingEntity.setFood(foodEntity);
            feedingEntity.setFeedingTime(LocalDate.parse(item.getFeedingTime()));
            feedingEntity.setQuantity(item.getQuantity());
            feedingRepository.save(feedingEntity);
        });
    }

    public void cloneSchedule(Integer days){
        LocalDate date = LocalDate.now();
        List<FeedingEntity> feedingList = feedingRepository.getAllByFeedingTime(date);
        feedingList.forEach(feedingEntity -> {
            for(int i = 1; i < days + 1; i++) {
                FeedingEntity cloneEntity = new FeedingEntity();
                LocalDate newDate = date.plusDays(i);
                cloneEntity.setAnimal(feedingEntity.getAnimal());
                cloneEntity.setFood(feedingEntity.getFood());
                cloneEntity.setFeedingTime(newDate);
                cloneEntity.setQuantity(feedingEntity.getQuantity());
                feedingRepository.save(cloneEntity);
            }
        });
    }

    public FeedingList getFeedingList(Integer days){
        FeedingList feedingList = new FeedingList();
        LocalDate initDate = LocalDate.now();
        LocalDate endDate;
        if (days == 1) {
            endDate=initDate;
        } else
        {endDate = initDate.plusDays(days-1);}
        List<FeedingListItem> itemsList = new ArrayList<>();
        List<FeedingDTO> foodList = feedingRepository.getFoodByDays(initDate,endDate);
        foodList.forEach(feedingDTO -> {
            FeedingListItem item = new FeedingListItem();
            item.setFoodId(feedingDTO.getFood().getId());
            item.setName(feedingDTO.getFood().getName());
            item.setCountForDays(feedingDTO.getQuantity());
            item.setCount(feedingDTO.getFood().getCount());
            long missing = feedingDTO.getFood().getCount() - feedingDTO.getQuantity();
            if (missing < 0) {
                item.setMissing(Math.abs(missing));
            }
            item.setUnit(feedingDTO.getFood().getUnit());
            itemsList.add(item);
        });
        feedingList.setDays(days);
        feedingList.setFood(itemsList);
        return feedingList;
    }

}
