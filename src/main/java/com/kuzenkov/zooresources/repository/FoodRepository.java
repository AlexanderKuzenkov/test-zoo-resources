package com.kuzenkov.zooresources.repository;

import com.kuzenkov.zooresources.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
}
