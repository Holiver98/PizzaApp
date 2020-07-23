package com.github.holiver98.service;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import com.github.holiver98.model.User;
import com.github.holiver98.repository.IRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RatingService implements IRatingService {
    @Autowired
    private IRatingRepository ratingRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IPizzaService pizzaService;

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

        Rating ratingOfUserOnThisPizza = ratingRepository.findByUserEmailAddressAndPizzaId(user.getEmailAddress(), pizzaId);
        if(ratingOfUserOnThisPizza != null){
            System.out.println("User already rated this pizza!");
            return;
        }

        Rating ratingToGive = new Rating();
        ratingToGive.setPizzaId(pizzaId);
        ratingToGive.setRating(rating);
        ratingToGive.setUserEmailAddress(user.getEmailAddress());
        ratingRepository.save(ratingToGive);
        pizzaService.recalculateRatingAverage(pizzaId);
    }

    @Override
    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getRatingsOfPizza(long pizzaId) {
        return ratingRepository.findByPizzaId(pizzaId);
    }

    @Override
    public List<Rating> getRatingsOfUser(String userEmailAddress) {
        return ratingRepository.findByUserEmailAddress(userEmailAddress);
    }

    private boolean isRatingValid(int rating) {
        if(rating < 1 || rating > 5){
            return false;
        }else{
            return true;
        }
    }
}
