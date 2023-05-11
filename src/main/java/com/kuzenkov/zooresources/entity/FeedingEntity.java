package com.kuzenkov.zooresources.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "feeding_schedule")
@AllArgsConstructor
@NoArgsConstructor
public class FeedingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private AnimalEntity animal;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private FoodEntity food;

    @Column(name = "feedingTime")
    private LocalDate feedingTime;

    @Column(name = "quantity")
    private Long quantity;
}
