package Model;

import java.util.Objects;

public class Rating {
    private String pizzaName;
    private int rating;
    private String userEmailAddress;

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

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

    @Override
    public String toString() {
        return "Rating{" +
                "pizzaName='" + pizzaName + '\'' +
                ", rating=" + rating +
                ", userEmailAddress='" + userEmailAddress + '\'' +
                '}';
    }
}
