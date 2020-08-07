package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.Rating;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryRatingDao implements IInMemoryRatingDao {
    private List<Rating> ratings = new ArrayList<Rating>();

    @Override
    public Optional<Rating> saveRating(Rating rating)
    {
        if(rating == null){
            throw new NullPointerException("rating is null");
        }

        Optional<Rating> dbRating = getRatingOfUserForPizza(rating.getUserEmailAddress(), rating.getPizzaId());
        if(dbRating.isPresent()){
            //Already in database
            return Optional.empty();
        }

        ratings.add(rating);
        return Optional.of(rating);
    }

    @Override
    public List<Rating> getRatings() {
        return ratings;
    }

    @Override
    public List<Rating> getRatingsOfPizza(long pizzaId) {
        return ratings.stream()
                .filter(r -> r.getPizzaId() == pizzaId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Rating> getRatingsOfUser(String userEmailAddress) {
        return ratings.stream()
                .filter(r -> r.getUserEmailAddress().equals(userEmailAddress))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Rating> getRatingOfUserForPizza(String userEmailAddress, long pizzaId) {
        if (userEmailAddress == null){
            throw new NullPointerException("userEmailAddress is null");
        }
        return ratings.stream()
                .filter(r -> r.getUserEmailAddress().equals(userEmailAddress)
                        && r.getPizzaId() == pizzaId)
                .findFirst();
    }

    @Override
    public int updateRating(Rating rating) {
        if(rating == null){
            throw new NullPointerException("rating is null");
        }

        Optional<Rating> oldRating = getRatingOfUserForPizza(rating.getUserEmailAddress(), rating.getPizzaId());
        if(oldRating.isPresent()){
            updateOldRatingWithNew(oldRating.get(), rating);
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public int deleteRating(long pizzaId, String userEmailAddress) {
        if(userEmailAddress == null){
            throw new NullPointerException("userEmailAddress is null");
        }
        Optional<Rating> dbRating = getRatingOfUserForPizza(userEmailAddress, pizzaId);
        if(dbRating.isPresent()){
            ratings.remove(dbRating.get());
            return 1;
        }else{
            return -1;
        }
    }

    private void updateOldRatingWithNew(Rating oldRating, Rating newRating){
        oldRating.setPizzaId(newRating.getPizzaId());
        oldRating.setUserEmailAddress(newRating.getUserEmailAddress());
        oldRating.setRating(newRating.getRating());
    }
}
