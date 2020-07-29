package com.github.holiver98.service;

public class NoPermissionException extends RuntimeException{
    public NoPermissionException(String message){
        super(message);
    }
}
