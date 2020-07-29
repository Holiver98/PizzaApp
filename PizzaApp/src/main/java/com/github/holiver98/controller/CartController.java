package com.github.holiver98.controller;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.service.*;
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
        try {
            cartService.addPizzaToCart(pizza);
        } catch (CartIsFullException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/remove")
    public void removePizzaFromCart(@RequestBody Pizza pizza){
        cartService.removePizzaFromCart(pizza);
    }

    @GetMapping("/order")
    public void placeOrder(){
        try {
            cartService.placeOrder();
        } catch (CartIsEmptyException e) {
            e.printStackTrace();
        }
    }
}
