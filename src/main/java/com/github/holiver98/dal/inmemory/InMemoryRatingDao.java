package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Rating;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryRatingDao implements IInMemoryRatingDao {
    private List<Rating> ratings = new ArrayList<Rating>();

    @Override
    public void saveRating(Rating rating) {
        ratings.add(rating);
    }

    @Override
    public List<Rating> getRatings() {
        return ratings;
    }

    @Override
    public List<Rating> getRatingsOfPizza(long pizzaId) {
        return ratings.stream()
                .filter(r -> r.getPizzaId() == pizzaId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Rating> getRatingsOfUser(String userEmailAddress) {
        return ratings.stream()
                .filter(r -> r.getUserEmailAddress().equals(userEmailAddress))
                .collect(Collectors.toList());
    }

    @Override
    public Rating getRatingOfUserForPizza(String userEmailAddress, long pizzaId) {
        return ratings.stream()
                .filter(r -> r.getUserEmailAddress().equals(userEmailAddress)
                        && r.getPizzaId() == pizzaId)
                .findFirst().orElse(null);
    }

    @Override
    public void updateRating(Rating rating) {

    }

    @Override
    public void deleteRating(long pizzaId, String userEmailAddress) {

    }
}
