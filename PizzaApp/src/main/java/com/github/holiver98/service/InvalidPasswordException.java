package com.github.holiver98.service;

public class InvalidPasswordException extends InvalidInputException{
    public InvalidPasswordException(String message){
        super(message);
    }
}
