package com.github.holiver98.service;

import com.github.holiver98.model.User;

//TODO: +1 a hibakezelésre és annak kiszervezésére, de nem a öröklés erre a legalkalmasabb módszer, inkább valamilyen Validátor osztály.
public abstract class UserServiceBaseExceptionHandler implements IUserService {
    @Override
    public void login(String emailAddress, String password) throws IncorrectPasswordException, NotFoundException {
        if(password == null){
            throw new NullPointerException("password was null");
        }else if(emailAddress == null){
            throw new NullPointerException("emailAddress was null");
        }
    }

    @Override
    public void logout(String emailAddress) throws NotFoundException{
        if(emailAddress == null){
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