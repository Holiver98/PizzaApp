package database;

import model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InMemoryDatabase {
    public Set<User> users;
    public Set<Rating> ratings;
    public Set<Pizza> pizzas;
    public Set<Order> orders;
    public Set<Ingredient> ingredients;

    public InMemoryDatabase(){
        users = new HashSet<User>();
        ratings = new HashSet<Rating>();
        pizzas = new HashSet<Pizza>();
        orders = new HashSet<Order>();
        ingredients = new HashSet<Ingredient>();
    }
}
