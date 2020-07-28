package com.github.holiver98.service;

public class InvalidUsernameException extends InvalidInputException{
    public InvalidUsernameException(String message){
        super(message);
    }
}
