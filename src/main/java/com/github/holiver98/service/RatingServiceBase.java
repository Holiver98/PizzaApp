package com.github.holiver98.service;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import com.github.holiver98.model.User;

import java.util.List;
import java.util.Optional;

public abstract class RatingServiceBase implements IRatingService{
    protected IUserService userService;
    protected IPizzaService pizzaService;

    public RatingServiceBase(IUserService userService, IPizzaService pizzaService){
        this.userService = userService;
        this.pizzaService = pizzaService;
    }

    protected abstract Rating findByUserEmailAddressAndPizzaId(String email, long pizzaId);
    protected abstract void save(Rating rating);
    protected abstract List<Rating> getAll();
    protected abstract List<Rating> findByPizzaId(long pizzaId);
    protected abstract List<Rating> findByUserEmailAddress(String emailAddress);

    @Override
    public void ratePizza(long pizzaId, int rating) throws NoPermissionException, NotFoundException {
        User user = tryGetLoggedInUser();
        checkForExceptions(pizzaId, rating, user);
        Rating ratingToGive = createRating(pizzaId, rating, user.getEmailAddress());
        save(ratingToGive);
        recalculateRatingAverage(pizzaId);
    }

    @Override
    public List<Rating> getRatings() {
        return getAll();
    }

    @Override
    public List<Rating> getRatingsOfPizza(long pizzaId) {
        return findByPizzaId(pizzaId);
    }

    @Override
    public List<Rating> getRatingsOfUser(String userEmailAddress) {
        return findByUserEmailAddress(userEmailAddress);
    }

    protected boolean isRatingValid(int rating) {
        return rating >= 1 && rating <= 5;
    }

    private float recalculateRatingAverage(long pizzaId) throws NotFoundException {
        Pizza pizzaToUpdate = pizzaService.getPizzaById(pizzaId).orElseThrow(() -> new NotFoundException("No pizza exists with this id (" + pizzaId +")!"));
        List<Rating> ratings = getRatingsOfPizza(pizzaId);
        float newRatingAverage = calculateNewRatingAverage(ratings);
        pizzaToUpdate.setRatingAverage(newRatingAverage);
        pizzaService.updatePizzaWithoutAuthentication(pizzaToUpdate);
        return newRatingAverage;
    }

    private float calculateNewRatingAverage(List<Rating> ratings) {
        if(ratings.size() == 0){
            return 0;
        }

        int ratingSum = 0;
        for (Rating rating : ratings) {
            ratingSum += rating.getRating();
        }
        return (float)ratingSum / (float)ratings.size();
    }

    private Rating createRating(long pizzaId, int rating, String emailAddress) {
        Rating newRating = new Rating();
        newRating.setPizzaId(pizzaId);
        newRating.setRating(rating);
        newRating.setUserEmailAddress(emailAddress);
        return newRating;
    }

    private User tryGetLoggedInUser() throws NoPermissionException {
        User user = userService.getLoggedInUser();
        if(user == null){
            throw new NoPermissionException("Only logged in users may rate pizzas.");
        }
        return user;
    }

    private void checkForExceptions(long pizzaId, int rating, User user) throws NotFoundException {
        checkIfRatingValid(rating);
        checkIfPizzaExists(pizzaId);
        checkIfUserAlreadyRatedThisPizza(pizzaId, user);
    }

    private void checkIfRatingValid(int rating) {
        if(!isRatingValid(rating)){
            throw new IllegalArgumentException("Invalid rating, must be between 1 and 5, but was: (" + rating + ")");
        }
    }

    private void checkIfPizzaExists(long pizzaId) throws NotFoundException {
        pizzaService.getPizzaById(pizzaId).orElseThrow(() -> new NotFoundException("No pizza exists with this id (" + pizzaId +")!"));
    }

    private void checkIfUserAlreadyRatedThisPizza(long pizzaId, User user) {
        Rating ratingOfUserOnThisPizza = findByUserEmailAddressAndPizzaId(user.getEmailAddress(), pizzaId);
        if(ratingOfUserOnThisPizza != null){
            throw new UnsupportedOperationException("User ("+ user.getEmailAddress() +") already rated this pizza!");
        }
    }
}
