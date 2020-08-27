package com.github.holiver98.service;

import com.github.holiver98.model.Rating;

import java.util.List;

public interface IRatingService {
    /**
     * Give the pizza with the given id the given rating by the currently logged in user.
     * Also updates the ratingAverage of the pizza.
     *
     * @param pizzaId The id of the pizza to be rated.
     * @param rating A rating of 1-5 to give the pizza.
     * @param emailAddress the email address of the user, who is rating the pizza.
     * @throws IllegalArgumentException if rating is invalid.
     * @throws NotFoundException if the pizza to be rated does not exist.
     * @throws UnsupportedOperationException if the user already rated the given pizza.
     * @throws NullPointerException if emailAddress is null.
     * @throws NotRegisteredException if no user is registered with this emailAddress.
     */
    void ratePizza(long pizzaId, int rating, String emailAddress) throws NotFoundException;

    /**
     * Gets all the ratings from the database.
     *
     * @return A list of all the ratings.
     */
    List<Rating> getRatings();

    /**
     * Gets all the ratings of the given pizza.
     *
     * @param pizzaId The id of the pizza, which ratings we want to know.
     * @return A list of the ratings of the given pizza.
     */
    List<Rating> getRatingsOfPizza(long pizzaId);

    /**
     * Get all the ratings that the given user has made.
     *
     * @param userEmailAddress The email address, that identifies the user.
     * @return A list of the ratings, that the user made.
     */
    List<Rating> getRatingsOfUser(String userEmailAddress);
}
