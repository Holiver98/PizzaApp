package com.github.holiver98.controller;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
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
    public void addPizzaToCart(@RequestBody Pizza pizza) throws CartIsFullException {
        cartService.addPizzaToCart(pizza);
    }

    @PostMapping("/remove")
    public void removePizzaFromCart(@RequestBody Pizza pizza){
        cartService.removePizzaFromCart(pizza);
    }

    @PostMapping("/order")
    public void placeOrder(@RequestBody String userEmailAddress) throws CartIsEmptyException, MessagingException, NotFoundException {
        cartService.placeOrder(userEmailAddress);
    }
}
