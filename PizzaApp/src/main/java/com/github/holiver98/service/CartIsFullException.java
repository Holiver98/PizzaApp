package com.github.holiver98.service;

public class CartIsFullException extends Exception{
    public CartIsFullException(String message){
        super(message);
    }
}
