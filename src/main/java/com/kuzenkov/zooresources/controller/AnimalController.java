package com.kuzenkov.zooresources.controller;

import com.kuzenkov.zooresources.api.model.*;
import com.kuzenkov.zooresources.service.animal.AnimalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/animal")
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping("/list")
    public AnimalList getAllAnimal() {
        return animalService.getAllAnimal();
    }

    @PostMapping
    public Long addAnimal(@Valid @RequestBody AnimalCreate animalCreate) {
        return animalService.addAnimal(animalCreate);
    }

    @GetMapping("/{animal-id}")
    public Animal getAnimal(@PathVariable(value = "animal-id") Long animalId) {
        return animalService.getById(animalId);
    }

    @PutMapping("/{animal-id}")
    public void putAnimal(@PathVariable(value = "animal-id") Long animalId,
                                          @Valid @RequestBody AnimalPut animalPut) {
        animalService.changeAnimal(animalId, animalPut);
    }

    @DeleteMapping("/{animal-id}")
    public void deleteAnimal(@PathVariable(value = "animal-id") Long animalId) {
        animalService.deleteAnimal(animalId);
    }

    @PostMapping("/batch-delete")
    public void batchDelete(@Valid @RequestBody AnimalDeleteRequest request) {
        animalService.deleteAnimalByList(request.getIdList());
    }

    @GetMapping("{animal-id}/feeding")
    public AnimalFeeding getFeedingByAnimal(@PathVariable(value = "animal-id") Long animalId) {
        return animalService.getAnimalFeeding(animalId);
    }
}
