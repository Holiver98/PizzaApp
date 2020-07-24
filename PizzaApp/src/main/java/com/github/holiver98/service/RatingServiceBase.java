package com.github.holiver98.service;

import com.github.holiver98.dal.jpa.IRatingRepository;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import com.github.holiver98.model.User;
import org.springframework.beans.factory.annotation.Autowired;

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
        if(!isRatingValid(rating)){
            System.out.println("Invalid rating: (" + rating + ")");
            return;
        }

        User user = userService.getLoggedInUser();
        if(user == null){
            System.out.println("You need to be logged in to rate pizzas!");
            return;
        }

        Pizza pizza = pizzaService.getPizzaById(pizzaId);
        if(pizza == null){
            System.out.println("No pizza exists with this id!");
            return;
        }

        Rating ratingOfUserOnThisPizza = findByUserEmailAddressAndPizzaId(user.getEmailAddress(), pizzaId);
        if(ratingOfUserOnThisPizza != null){
            System.out.println("User already rated this pizza!");
            return;
        }

        Rating ratingToGive = new Rating();
        ratingToGive.setPizzaId(pizzaId);
        ratingToGive.setRating(rating);
        ratingToGive.setUserEmailAddress(user.getEmailAddress());
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
        if(rating < 1 || rating > 5){
            return false;
        }else{
            return true;
        }
    }
}
