package com.github.holiver98.database;

import com.github.holiver98.model.*;

import java.util.ArrayList;
import java.util.List;

//TODO: +1, érdekes megközelítés, viszont inkább private field és getter, még akkor is, ha nincs igazán különbség a kettő között jelen esetben.
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
