package dao;

import database.InMemoryDatabase;
import model.Rating;

import java.util.List;
//TODO: implement class
public class InMemoryRatingDao implements IRatingDao {
    private InMemoryDatabase dbContext;

    public InMemoryRatingDao(InMemoryDatabase context) {
        dbContext = context;
    }

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