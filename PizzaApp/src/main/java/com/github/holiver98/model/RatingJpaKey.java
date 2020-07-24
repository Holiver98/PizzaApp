package com.github.holiver98.model;

import java.io.Serializable;

public class RatingJpaKey implements Serializable {
    private Long pizzaId;
    private int rating;
    private String userEmailAddress;
}
