package com.github.holiver98.service;

import com.github.holiver98.model.User;

public abstract class UserServiceBaseExceptionHandler implements IUserService {
    @Override
    public void login(String emailAddress, String password) throws IncorrectPasswordException, NotRegisteredException {
        if(password == null){
            throw new NullPointerException("password is null");
        }else if(emailAddress == null){
            throw new NullPointerException("emailAddress is null");
        }
    }

    @Override
    public void register(User user) throws InvalidInputException, AlreadyRegisteredException {
        if(user == null) {
            throw new NullPointerException("user is null");
        }
    }
}
