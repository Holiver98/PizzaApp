package com.github.holiver98.service;

public class InvalidEmailException extends InvalidInputException{
    public InvalidEmailException(String message){
        super(message);
    }
}
