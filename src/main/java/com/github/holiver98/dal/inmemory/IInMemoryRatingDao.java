package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Rating;

import java.util.List;
import java.util.Optional;

public interface IInMemoryRatingDao {
    /**
     * Saves the rating. Each user can have only 1 rating on 1 pizza.
     *
     * @return The saved rating. If rating already exists, null is returned.
     * @throws NullPointerException if rating is null.
     */
    Optional<Rating> saveRating(Rating rating);

    List<Rating> getRatings();

    /**
     * Gets all the ratings on the given pizza.
     *
     * @param pizzaId The id of the pizza, which ratings we want to know.
     * @return A list of all the ratings on the given pizza.
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
     * @throws NullPointerException if userEmailAddress is null.
     */
    Optional<Rating> getRatingOfUserForPizza(String userEmailAddress, long pizzaId);

    /**
     * Updates the rating, that has the same id, as the given rating argument, with
     * the values of the rating argument.
     *
     * @param rating The rating to be updated, with the new values.
     * @return 1 - success, -1 - rating doesn't exist.
     * @throws NullPointerException if rating is null.
     */
    int updateRating(Rating rating);

    /**
     * Deletes the rating.
     *
     * @param pizzaId The id of the pizza, which was rated.
     * @param userEmailAddress The email address of the user, who rated the pizza.
     * @return 1 - success, -1 - if no rating exists on the pizza (with this pizzaId)
     * by the user (identified by this userEmailAddress).
     * @throws NullPointerException if userEmailAddress is null.
     */
    int deleteRating(long pizzaId, String userEmailAddress);
}
