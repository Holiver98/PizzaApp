package model;

import java.util.Objects;

public class Rating {
    private long pizzaId;
    private int rating;
    private String userEmailAddress;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    public long getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(long pizzaId) {
        this.pizzaId = pizzaId;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "pizzaId=" + pizzaId +
                ", rating=" + rating +
                ", userEmailAddress='" + userEmailAddress + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;
        Rating rating1 = (Rating) o;
        return pizzaId == rating1.pizzaId &&
                rating == rating1.rating &&
                userEmailAddress.equals(rating1.userEmailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pizzaId, rating, userEmailAddress);
    }
}
