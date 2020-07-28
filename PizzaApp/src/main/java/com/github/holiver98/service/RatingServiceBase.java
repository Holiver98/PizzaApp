package com.github.holiver98.service;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import com.github.holiver98.model.User;

import java.util.List;

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
    public void ratePizza(long pizzaId, int rating) {
        User user = tryGetLoggedInUser();
        checkForExceptions(pizzaId, rating, user);
        Rating ratingToGive = createRating(pizzaId, rating, user.getEmailAddress());
        save(ratingToGive);
        pizzaService.recalculateRatingAverage(pizzaId);
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

    private Rating createRating(long pizzaId, int rating, String emailAddress) {
        Rating newRating = new Rating();
        newRating.setPizzaId(pizzaId);
        newRating.setRating(rating);
        newRating.setUserEmailAddress(emailAddress);
        return newRating;
    }

    private User tryGetLoggedInUser() {
        User user = userService.getLoggedInUser();
        if(user == null){
            throw new NoPermissionException("Only logged in users may rate pizzas.");
        }
        return user;
    }

    private void checkForExceptions(long pizzaId, int rating, User user) {
        checkIfRatingValid(rating);
        checkIfPizzaExists(pizzaId);
        checkIfUserAlreadyRatedThisPizza(pizzaId, user);
    }

    private void checkIfRatingValid(int rating) {
        if(!isRatingValid(rating)){
            throw new IllegalArgumentException("Invalid rating, must be between 1 and 5, but was: (" + rating + ")");
        }
    }

    private void checkIfPizzaExists(long pizzaId) {
        Pizza pizza = pizzaService.getPizzaById(pizzaId);
        if(pizza == null){
            throw new NotFoundException("No pizza exists with this id!");
        }
    }

    private void checkIfUserAlreadyRatedThisPizza(long pizzaId, User user) {
        Rating ratingOfUserOnThisPizza = findByUserEmailAddressAndPizzaId(user.getEmailAddress(), pizzaId);
        if(ratingOfUserOnThisPizza != null){
            throw new UnsupportedOperationException("User ("+ user.getEmailAddress() +") already rated this pizza!");
        }
    }

    public static class NoPermissionException extends RuntimeException{
        public NoPermissionException(String message){
            super(message);
        }
    }

    public static class NotFoundException extends RuntimeException{
        public NotFoundException(String message){
            super(message);
        }
    }
}
