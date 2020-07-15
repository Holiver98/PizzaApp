package dao;

import model.Pizza;
import model.Rating;

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
     * @param pizza The pizza, which ratings we want to know.
     * @return A list of all the ratings of the given pizza.
     */
    List<Rating> getRatingsOfPizza(Pizza pizza);

    /**
     * Get all the ratings that the given user has made.
     *
     * @param userEmailAddress The email address, that identifies the user.
     * @return A list of the ratings, that the user made.
     */
    List<Rating> getRatingsOfUser(String userEmailAddress);

    /**
     * Updates the ratings in the database, that ahs the same id, as the rating argument.
     *
     * @param rating The rating to be updated.
     */
    void updateRating(Rating rating);

    /**
     * Deletes the rating from the database, if it exists.
     *
     * @param rating The rating to be deleted.
     */
    void deleteRating(Rating rating);
}
