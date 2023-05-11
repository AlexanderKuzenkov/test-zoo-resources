package com.kuzenkov.zooresources.repository;

import com.kuzenkov.zooresources.entity.AnimalEntity;
import com.kuzenkov.zooresources.entity.FeedingEntity;
import com.kuzenkov.zooresources.entity.FoodEntity;
import com.kuzenkov.zooresources.service.feeding.FeedingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FeedingRepository extends JpaRepository<FeedingEntity, Long> {

    List<FeedingEntity> getAllByAnimal(AnimalEntity animalEntity);

    List<FeedingEntity> getAllByFeedingTime(LocalDate date);

    @Query("SELECT e.food as food, sum(e.quantity) as quantity " +
            "FROM FeedingEntity e WHERE e.feedingTime BETWEEN :initDate AND :endDate GROUP BY e.food")
    List<FeedingDTO> getFoodByDays(LocalDate initDate, LocalDate endDate);

    @Modifying
    @Query("DELETE FROM FeedingEntity e WHERE e.animal.id = :animalId")
    void deleteAllByAnimal(Long animalId);

    @Modifying
    @Query("DELETE FROM FeedingEntity e WHERE e.food.id = :foodId")
    void deleteAllByFood(Long foodId);
}
