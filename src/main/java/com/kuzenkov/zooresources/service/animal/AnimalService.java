package com.kuzenkov.zooresources.service.animal;

import com.kuzenkov.zooresources.api.model.*;

import java.util.List;

public interface AnimalService {

    AnimalList getAllAnimal();

    Long addAnimal(AnimalCreate animalCreate);

    Animal getById(Long animalId);

    void changeAnimal(Long animalId, AnimalPut animalPut);

    void deleteAnimal(Long animalId);

    void deleteAnimalByList(List<Long> idList);

    AnimalFeeding getAnimalFeeding(Long animalId);
}
