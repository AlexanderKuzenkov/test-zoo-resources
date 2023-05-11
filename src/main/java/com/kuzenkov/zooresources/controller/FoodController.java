package com.kuzenkov.zooresources.controller;

import com.kuzenkov.zooresources.api.model.*;
import com.kuzenkov.zooresources.service.food.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/food")
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/list")
    public FoodList getAllFood() {
        return foodService.getAllFood();
    }

    @PostMapping
    public Long addFood(@Valid @RequestBody FoodCreate foodCreate) {
        return foodService.addFood(foodCreate);
    }

    @GetMapping("/{food-id}")
    public Food getFood(@PathVariable(value = "food-id") Long foodId) {
        return foodService.getById(foodId);
    }

    @PutMapping("/{food-id}")
    public void changeFood(@PathVariable(value = "food-id") Long foodId,
                           @Valid @RequestBody FoodPut foodPut) {
        foodService.changeFood(foodId, foodPut);
    }

    @DeleteMapping("/{food-id}")
    public void deleteFood(@PathVariable(value = "food-id") Long foodId) {
        foodService.deleteFood(foodId);
    }

    @PostMapping("/batch-delete")
    public void batchDelete(@RequestBody FoodDeleteRequest request) {
        foodService.deleteFoodByList(request.getIdList());
    }
}
