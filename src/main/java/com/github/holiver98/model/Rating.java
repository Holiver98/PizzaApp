package com.github.holiver98.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "ratings")
@IdClass(RatingJpaKey.class)
@Data
public class Rating {
    @Id
    @JoinColumn(name = "pizza_id", referencedColumnName = "id")
    private Long pizzaId;
    @Id
    private int rating;
    @Id
    @Column(name = "email")
    private String userEmailAddress;
}
