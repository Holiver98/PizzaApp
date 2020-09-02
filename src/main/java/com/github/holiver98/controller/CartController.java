package com.github.holiver98.controller;

import com.github.holiver98.service.CartIsEmptyException;
import com.github.holiver98.service.ICartService;
import com.github.holiver98.service.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping(value = "/cart")
public class CartController {
    @Autowired
    private ICartService cartService;

    /*@PostMapping("/order")
    public void placeOrder(@RequestBody String emailAddress) throws CartIsEmptyException, MessagingException, NotFoundException {
        cartService.placeOrder(emailAddress);
    }*/
}
