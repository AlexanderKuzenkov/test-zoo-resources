package com.kuzenkov.zooresources.service.food;

import com.kuzenkov.zooresources.api.model.*;

import java.util.List;

public interface FoodService {

    FoodList getAllFood();

    Long addFood(FoodCreate foodCreate);

    Food getById(Long foodId);

    void changeFood(Long foodId, FoodPut foodPut);

    void deleteFood(Long foodId);

    void deleteFoodByList(List<Long> idList);
}
