package com.github.holiver98.service.inmemory;

import com.github.holiver98.dal.inmemory.IInMemoryRatingDao;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import com.github.holiver98.model.User;
import com.github.holiver98.service.IPizzaService;
import com.github.holiver98.service.IRatingService;
import com.github.holiver98.service.IUserService;
import com.github.holiver98.service.RatingServiceBase;

import java.util.List;

public class InMemoryRatingService extends RatingServiceBase {
    private IInMemoryRatingDao ratingDao;

    public InMemoryRatingService(IInMemoryRatingDao dao, IUserService userService, IPizzaService pizzaService){
        super(userService, pizzaService);
        ratingDao = dao;
    }

    @Override
    protected Rating findByUserEmailAddressAndPizzaId(String email, long pizzaId) {
        return ratingDao.getRatingOfUserForPizza(email, pizzaId);
    }

    @Override
    protected void save(Rating rating) {
        ratingDao.saveRating(rating);
    }

    @Override
    protected List<Rating> getAll() {
        return ratingDao.getRatings();
    }

    @Override
    protected List<Rating> findByPizzaId(long pizzaId) {
        return ratingDao.getRatingsOfPizza(pizzaId);
    }

    @Override
    protected List<Rating> findByUserEmailAddress(String emailAddress) {
        return ratingDao.getRatingsOfUser(emailAddress);
    }
}
