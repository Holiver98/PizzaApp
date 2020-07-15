package service;

import model.Pizza;
import model.Rating;

import java.util.List;

public interface IRatingService {
    void ratePizza(Pizza pizza, int rating);
    List<Rating> getRatings();
    List<Rating> getRatingsOfPizza(Pizza pizza);
}
