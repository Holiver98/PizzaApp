package model;

import java.util.Set;

public class Pizza {
    private long id;
    private String name;
    private Set<Ingredient> ingredients;
    private int radius;
    private float ratingAverage;
    private float price;
    private boolean isCustom;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getRadius() {
        return radius;
    }

    public float getRatingAverage() {
        return ratingAverage;
    }

    public float getPrice() {
        return price;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setRatingAverage(float ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", radius=" + radius +
                ", ratingAverage=" + ratingAverage +
                ", price=" + price +
                ", isCustom=" + isCustom +
                '}';
    }
}
