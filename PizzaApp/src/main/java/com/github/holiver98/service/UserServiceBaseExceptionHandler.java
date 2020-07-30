package com.github.holiver98.service;

import com.github.holiver98.model.User;

public abstract class UserServiceBaseExceptionHandler implements IUserService {
    @Override
    public void login(String emailAddress, String password) throws IncorrectPasswordException, NotRegisteredException {
        if(password == null){
            throw new NullPointerException("password was null");
        }else if(emailAddress == null){
            throw new NullPointerException("emailAddress was null");
        }
    }

    @Override
    public void register(User user) throws AlreadyExistsException {
        if(user == null) {
            throw new NullPointerException("user was null");
        }
    }
}
