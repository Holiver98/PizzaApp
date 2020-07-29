package com.github.holiver98.service;

public class CartIsEmptyException extends Exception{
    public CartIsEmptyException(String message){
        super(message);
    }
}
