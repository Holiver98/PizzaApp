package com.github.holiver98.controller;

import com.github.holiver98.model.User;
import com.github.holiver98.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    public User getLoggedInUser(){
        return userService.getLoggedInUser();
    }

    @PostMapping(value = "/register")
    public void register(@RequestBody User user) throws AlreadyRegisteredException {
        userService.register(user);
    }

    @PostMapping(value = "/login")
    public void login(@RequestBody LoginInfo loginInfo) throws IncorrectPasswordException, NotRegisteredException {
        userService.login(loginInfo.emailAddress, loginInfo.password);
    }

    @GetMapping(value = "/logout")
    public void logout(){
        userService.logout();
    }

    private static class LoginInfo{
        public String emailAddress;
        public String password;
    }
}
