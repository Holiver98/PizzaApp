package database;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDatabase {
    public List<User> users;
    public List<Rating> ratings;
    public List<Pizza> pizzas;
    public List<Order> orders;
    public List<Ingredient> ingredients;

    public InMemoryDatabase(){
        users = new ArrayList<User>();
        ratings = new ArrayList<Rating>();
        pizzas = new ArrayList<Pizza>();
        orders = new ArrayList<Order>();
        ingredients = new ArrayList<Ingredient>();
    }
}
