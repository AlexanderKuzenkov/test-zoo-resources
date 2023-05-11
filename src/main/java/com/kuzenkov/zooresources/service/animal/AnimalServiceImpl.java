package com.kuzenkov.zooresources.service.animal;

import com.kuzenkov.zooresources.api.model.*;
import com.kuzenkov.zooresources.entity.AnimalEntity;
import com.kuzenkov.zooresources.exception.AppNotFoundException;
import com.kuzenkov.zooresources.exception.AppValidationException;
import com.kuzenkov.zooresources.mapper.AnimalMapper;
import com.kuzenkov.zooresources.repository.AnimalRepository;
import com.kuzenkov.zooresources.repository.FeedingRepository;
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
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final FeedingRepository feedingRepository;

    public AnimalList getAllAnimal() {
        AnimalList result = new AnimalList();
        List<Animal> animalList = new ArrayList<>();
        animalRepository.findAll().forEach(entity -> {
                Animal animal = AnimalMapper.INSTANCE.toApiAnimal(entity);
                animalList.add(animal);
        });
        result.setPayload(animalList);
        result.setTotal(animalList.size());
        return result;
    }

    @Transactional
    public Long addAnimal(AnimalCreate animalCreate) {
        if (Objects.isNull(animalCreate.getName())) {
            throw new AppValidationException("Отсутствует обязательный параметр: 'имя животного'");
        }
        AnimalEntity entity = AnimalMapper.INSTANCE.createAnimal(animalCreate);
        return animalRepository.save(entity).getId();
    }

    public Animal getById(Long animalId) {
        AnimalEntity entity = animalRepository.findById(animalId)
                .orElseThrow(() -> new AppNotFoundException(String.format("Животное с id:%d не найдено", animalId)));
        return AnimalMapper.INSTANCE.toApiAnimal(entity);
    }

    @Transactional
    public void changeAnimal(Long animalId, AnimalPut animalPut) {
        AnimalEntity entity = animalRepository.findById(animalId)
                .orElseThrow(() -> new AppNotFoundException(String.format("Животное с id:%d не найдено", animalId)));
        entity.setName(animalPut.getName());
        entity.setSpecies(animalPut.getSpecies());
        entity.setIsCarnivorous(animalPut.getIsCarnivorous());
        animalRepository.save(entity);
    }

    @Transactional
    public void deleteAnimal(Long animalId) {
        feedingRepository.deleteAllByAnimal(animalId);
        animalRepository.deleteById(animalId);
    }

    @Transactional
    public void deleteAnimalByList(List<Long> selectionList) {
        selectionList.forEach(this::deleteAnimal);
    }

    public AnimalFeeding getAnimalFeeding(Long animalId) {
        AnimalFeeding animalFeeding = new AnimalFeeding();
        AnimalEntity animalEntity = animalRepository.findById(animalId)
                .orElseThrow(() -> new AppNotFoundException(String.format("Животное с id:%d не найдено", animalId)));
        List<AnimalFeedingItems> food = new ArrayList<>();
        feedingRepository.getAllByAnimal(animalEntity).forEach(feedingEntity -> {
            AnimalFeedingItems item = new AnimalFeedingItems();
            item.setFoodId(feedingEntity.getFood().getId());
            item.setFeedingTime(feedingEntity.getFeedingTime().toString());
            item.setQuantity(feedingEntity.getQuantity());
            food.add(item);
        });
        animalFeeding.setAnimalId(animalId);
        animalFeeding.setFood(food);
        return animalFeeding;
    }
}
