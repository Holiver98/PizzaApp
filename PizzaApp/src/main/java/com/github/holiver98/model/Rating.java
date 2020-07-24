package com.github.holiver98.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(RatingJpaKey.class)
@Data
public class Rating {
    @Id
    private long pizzaId;
    @Id
    private int rating;
    @Id
    private String userEmailAddress;
}
