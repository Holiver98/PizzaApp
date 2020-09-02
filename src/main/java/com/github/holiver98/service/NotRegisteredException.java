package com.github.holiver98.service;

public class NotRegisteredException extends RuntimeException {
    public NotRegisteredException(String message){
        super(message);
    }
}
