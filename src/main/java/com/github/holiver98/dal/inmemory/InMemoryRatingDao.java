package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Rating;
import java.util.ArrayList;
import java.util.List;
public class InMemoryRatingDao implements IInMemoryRatingDao {
    private List<Rating> ratings = new ArrayList<Rating>();

    @Override
    public void saveRating(Rating rating) {

    }

    @Override
    public List<Rating> getRatings() {
        return null;
    }

    @Override
    public List<Rating> getRatingsOfPizza(long pizzaId) {
        return null;
    }

    @Override
    public List<Rating> getRatingsOfUser(String userEmailAddress) {
        return null;
    }

    @Override
    public Rating getRatingOfUserForPizza(String userEmailAddress, long pizzaId) {
        return null;
    }

    @Override
    public void updateRating(Rating rating) {

    }

    @Override
    public void deleteRating(long pizzaId, String userEmailAddress) {

    }
}
