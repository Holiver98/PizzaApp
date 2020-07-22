package com.github.holiver98.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Rating {
    @Id
    private long pizzaId = -1;
    private int rating;
    private String userEmailAddress;
}
