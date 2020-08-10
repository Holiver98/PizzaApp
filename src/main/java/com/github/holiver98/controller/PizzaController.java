package com.github.holiver98.controller;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.service.AlreadyExistsException;
import com.github.holiver98.service.IPizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizza")
public class PizzaController {

    @Autowired
    IPizzaService pizzaService;

    @GetMapping("/list")
    public List<Pizza> getPizzas(){
        return pizzaService.getPizzas();
    }
}
