package com.github.holiver98.service.jpa;

import com.github.holiver98.model.Rating;
import com.github.holiver98.dal.jpa.IRatingRepository;
import com.github.holiver98.service.IPizzaService;
import com.github.holiver98.service.IUserService;
import com.github.holiver98.service.RatingServiceBase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JpaRatingService extends RatingServiceBase {
    @Autowired
    private IRatingRepository ratingRepository;

    public JpaRatingService(IUserService userService, IPizzaService pizzaService){
        super(userService, pizzaService);
    }

    @Override
    protected Rating findByUserEmailAddressAndPizzaId(String email, long pizzaId) {
        return ratingRepository.findByUserEmailAddressAndPizzaId(email, pizzaId);
    }

    @Override
    protected void save(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    protected List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    @Override
    protected List<Rating> findByPizzaId(long pizzaId) {
        return ratingRepository.findByPizzaId(pizzaId);
    }

    @Override
    protected List<Rating> findByUserEmailAddress(String emailAddress) {
        return ratingRepository.findByUserEmailAddress(emailAddress);
    }
}
