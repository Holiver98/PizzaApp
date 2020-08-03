package com.github.holiver98.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RatingJpaKey implements Serializable {
    private Long pizzaId;
    private int rating;
    private String userEmailAddress;
}
