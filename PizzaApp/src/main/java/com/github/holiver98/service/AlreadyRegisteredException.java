package com.github.holiver98.service;

public class AlreadyRegisteredException extends Exception{
    public AlreadyRegisteredException(String message){
        super(message);
    }
}
