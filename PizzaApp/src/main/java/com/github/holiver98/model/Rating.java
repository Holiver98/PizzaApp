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
    private long pizzaId = -1;
    @Id
    private int rating;
    @Id
    private String userEmailAddress;
}
