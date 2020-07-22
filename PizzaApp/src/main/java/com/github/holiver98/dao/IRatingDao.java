package com.github.holiver98.dao;

import com.github.holiver98.model.Rating;

import java.util.List;

public interface IRatingDao {
    /**
     * Saves the rating into database.
     * It does not return the generated id, because the rating contains the user's
     * email address, which identifies every rating, because each user can rate each pizza
     * only once.
     *
     * @param rating The rating to be saved.
     */
    void saveRating(Rating rating);

    /**
     * Gets all the ratings from the database.
     *
     * @return A list of all the ratings.
     */
    List<Rating> getRatings();

    /**
     * Gets all the ratings from the database, of a given pizza.
     *
     * @param pizzaId The id of the pizza, which ratings we want to know.
     * @return A list of all the ratings of the given pizza.
     */
    List<Rating> getRatingsOfPizza(long pizzaId);

    /**
     * Get all the ratings that the given user has made.
     *
     * @param userEmailAddress The email address, that identifies the user.
     * @return A list of the ratings, that the user made.
     */
    List<Rating> getRatingsOfUser(String userEmailAddress);

    /**
     * Gets the rating, that the user made on the pizza with the given id.
     * Each user can only have 1 rating for 1 pizza.
     *
     * @param userEmailAddress The email address of the user.
     * @param pizzaId The pizza we want the rating of.
     * @return The rating the user made on this pizza, or null if the user didn't rate the pizza.
     */
    Rating getRatingOfUserForPizza(String userEmailAddress, long pizzaId);

    /**
     * Updates the ratings in the database, that ahs the same id, as the rating argument.
     *
     * @param rating The rating to be updated.
     */
    void updateRating(Rating rating);

    /**
     * Deletes the rating from the database, if it exists.
     * The pizzaId and the userEmailAddress identifies the rating, because each user can
     * only rate a pizza once.
     *
     * @param pizzaId The id of the pizza, which was rated.
     * @param userEmailAddress The email address of the user, who rated the pizza.
     */
    void deleteRating(long pizzaId, String userEmailAddress);
}
