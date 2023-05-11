package com.kuzenkov.zooresources.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "animal")
@AllArgsConstructor
@NoArgsConstructor
public class AnimalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "species")
    private String species;

    @Column(name = "carnivorous")
    private Boolean isCarnivorous;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "animal")
    private Set<FeedingEntity> feeding;

}
