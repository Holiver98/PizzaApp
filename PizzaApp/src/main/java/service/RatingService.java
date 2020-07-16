package service;

import dao.IRatingDao;
import model.Pizza;
import model.Rating;
import model.User;

import java.util.List;

public class RatingService implements IRatingService{

    private IRatingDao ratingDao;
    private IUserService userService;
    private IPizzaService pizzaService;

    public RatingService(IRatingDao dao, IUserService userService, IPizzaService pizzaService){
        ratingDao = dao;
        this.userService = userService;
        this.pizzaService = pizzaService;
    }

    @Override
    public void ratePizza(long pizzaId, int rating) {
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

        Rating ratingOfUserOnThisPizza = ratingDao.getRatingOfUserForPizza(user.getEmailAddress(), pizzaId);
        if(ratingOfUserOnThisPizza != null){
            System.out.println("User already rated this pizza!");
            return;
        }

        Rating ratingToGive = new Rating();
        ratingToGive.setPizzaId(pizzaId);
        ratingToGive.setRating(rating);
        ratingToGive.setUserEmailAddress(user.getEmailAddress());
        ratingDao.saveRating(ratingToGive);
        pizzaService.recalculateRatingAverage(pizzaId);
    }

    @Override
    public List<Rating> getRatings() {
        return ratingDao.getRatings();
    }

    @Override
    public List<Rating> getRatingsOfPizza(Pizza pizza) {
        return ratingDao.getRatingsOfPizza(pizza);
    }

    @Override
    public List<Rating> getRatingsOfUser(String userEmailAddress) {
        return ratingDao.getRatingsOfUser(userEmailAddress);
    }
}
