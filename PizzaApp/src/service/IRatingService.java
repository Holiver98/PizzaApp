package service;

import model.Pizza;
import model.Rating;

import java.util.List;

public interface IRatingService {
    /**
     * Give the pizza the given rating by the currently logged in user.
     *
     * @param pizza The pizza to be rated.
     * @param rating A rating of 1-5 to give the pizza.
     */
    void ratePizza(Pizza pizza, int rating);

    /**
     * Gets all the ratings from the database.
     *
     * @return A list of all the ratings.
     */
    List<Rating> getRatings();

    /**
     * Gets all the ratings of the given pizza.
     *
     * @param pizza The pizza, which ratings we want to know.
     * @return A list of the ratings of the given pizza.
     */
    List<Rating> getRatingsOfPizza(Pizza pizza);

    /**
     * Get all the ratings that the given user has made.
     *
     * @param userEmailAddress The email address, that identifies the user.
     * @return A list of the ratings, that the user made.
     */
    List<Rating> getRatingsOfUser(String userEmailAddress);
}
