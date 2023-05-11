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
@Table(name = "food")
@AllArgsConstructor
@NoArgsConstructor
public class FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "count")
    private long count;

    @Column(name = "unit")
    private String unit;

    @Column(name = "type")
    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "food")
    private Set<FeedingEntity> feeding;
}
