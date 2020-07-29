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
    public void register(@RequestBody User user){
        tryRegister(user);
    }

    @PostMapping(value = "/login")
    public void login(@RequestBody LoginInfo loginInfo){
        tryLogin(loginInfo);
    }

    @GetMapping(value = "/logout")
    public void logout(){
        userService.logout();
    }

    private void tryLogin(LoginInfo loginInfo) {
        try {
            userService.login(loginInfo.emailAddress, loginInfo.password);
        } catch (IncorrectPasswordException e) {
            e.printStackTrace();
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        }
    }

    private void tryRegister(User user) {
        try {
            userService.register(user);
        }catch (AlreadyRegisteredException e) {
            e.printStackTrace();
        }
    }

    private static class LoginInfo{
        public String emailAddress;
        public String password;
    }
}
