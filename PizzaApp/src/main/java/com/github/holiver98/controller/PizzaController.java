package com.github.holiver98.controller;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.service.IPizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PizzaController {

    @Autowired
    IPizzaService pizzaService;

    @GetMapping("/pizzas")
    public List<Pizza> getPizzas(){
        return pizzaService.getPizzas();
    }
}
