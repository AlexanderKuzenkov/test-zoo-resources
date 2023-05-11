package com.kuzenkov.zooresources.service.food;

import com.kuzenkov.zooresources.api.model.*;
import com.kuzenkov.zooresources.entity.FoodEntity;
import com.kuzenkov.zooresources.exception.AppNotFoundException;
import com.kuzenkov.zooresources.exception.AppValidationException;
import com.kuzenkov.zooresources.mapper.FoodMapper;
import com.kuzenkov.zooresources.repository.FeedingRepository;
import com.kuzenkov.zooresources.repository.FoodRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final FeedingRepository feedingRepository;

    public FoodList getAllFood() {
        FoodList result = new FoodList();
        List<Food> foodList = new ArrayList<>();
        foodRepository.findAll().forEach(entity -> {
                Food food = FoodMapper.INSTANCE.toApiFood(entity);
            foodList.add(food);
        });
        result.setPayload(foodList);
        result.setTotal(foodList.size());
        return result;
    }

    @Transactional
    public Long addFood(FoodCreate foodCreate) {
        if (Objects.isNull(foodCreate.getName())) {
            throw new AppValidationException("Отсутствует обязательный параметр: 'имя продукта'");
        }
        FoodEntity entity = FoodMapper.INSTANCE.createFood(foodCreate);
        Long id = foodRepository.save(entity).getId();
        return id;
    }

    public Food getById(Long foodId) {
        FoodEntity entity = foodRepository.findById(foodId)
                .orElseThrow(() -> new AppNotFoundException(String.format("Продукт с id:%d не найдено", foodId)));
        return FoodMapper.INSTANCE.toApiFood(entity);
    }

    @Transactional
    public void changeFood(Long foodId, FoodPut foodPut) {
        FoodEntity entity = foodRepository.findById(foodId)
                .orElseThrow(() -> new AppNotFoundException(String.format("Животное с id:%d не найдено", foodId)));
        entity.setName(foodPut.getName());
        entity.setCount(foodPut.getCount());
        entity.setUnit(foodPut.getUnit());
        entity.setType(foodPut.getType());
        foodRepository.save(entity);
    }

    @Transactional
    public void deleteFood(Long foodId) {
        feedingRepository.deleteAllByFood(foodId);
        foodRepository.deleteById(foodId);
    }

    @Transactional
    public void deleteFoodByList(List<Long> selectionList) {
        selectionList.forEach(this::deleteFood);
    }
}
