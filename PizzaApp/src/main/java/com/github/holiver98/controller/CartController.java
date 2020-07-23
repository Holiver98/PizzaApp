package com.github.holiver98.controller;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cart")
public class CartController {
    @Autowired
    private ICartService cartService;

    @GetMapping
    public List<Pizza> getCartContent(){
        return cartService.getCartContent();
    }

    @PostMapping("/add")
    public void addPizzaToCart(@RequestBody Pizza pizza){
        cartService.addPizzaToCart(pizza);
    }

    @PostMapping("/remove")
    public void removePizzaFromCart(@RequestBody Pizza pizza){
        cartService.removePizzaFromCart(pizza);
    }

    @GetMapping("/order")
    public void placeOrder(){
        cartService.placeOrder();
    }
}
