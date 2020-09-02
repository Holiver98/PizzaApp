package com.github.holiver98.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "ratings")
@IdClass(RatingJpaKey.class)
@Data
public class Rating {
    //Pizza objektumot kellett volna tárolni Long helyett és akkor a hibernate menedszelné nekem.
    //viszont most már túl fájdalmas lenne átírni. Most így nekem kell külön foglalkoznom vele,
    //hogy törlődjön amikor a hozzá tartozó pizza törlődik.
    @Id
    @JoinColumn(name = "pizza_id", referencedColumnName = "id")
    private Long pizzaId;
    @Id
    private int rating;
    @Id
    @Column(name = "email")
    private String userEmailAddress;
}
