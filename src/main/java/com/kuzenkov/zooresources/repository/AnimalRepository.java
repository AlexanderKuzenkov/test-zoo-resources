package com.kuzenkov.zooresources.repository;

import com.kuzenkov.zooresources.entity.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Long> {

}
