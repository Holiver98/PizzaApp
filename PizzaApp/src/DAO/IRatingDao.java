package DAO;

import Model.Pizza;
import Model.Rating;

import java.util.List;

public interface IRatingDao {
    void saveRating(Rating rating);
    List<Rating> getRatings();
    List<Rating> getRatingsForPizza(Pizza pizza);
    List<Rating> getRatingsOfUser(String userEmailAddress);
    void updateRating(Rating rating);
    void deleteRating(Rating rating);
}
