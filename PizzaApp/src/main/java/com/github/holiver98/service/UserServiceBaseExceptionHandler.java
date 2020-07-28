package com.github.holiver98.service;

import com.github.holiver98.model.User;

public abstract class UserServiceBaseExceptionHandler implements IUserService {
    @Override
    public void login(String emailAddress, String password) throws UserServiceBase.IncorrectPasswordException, UserServiceBase.NotRegisteredException {
        if(password == null){
            throw new NullPointerException("password is null");
        }else if(emailAddress == null){
            throw new NullPointerException("emailAddress is null");
        }
    }

    @Override
    public void register(User user) throws InvalidInputException, AlreadyRegisteredException {
        if(user == null) {
            throw new NullPointerException("user is null");
        }
    }

    public static class IncorrectPasswordException extends Exception{
        public IncorrectPasswordException(String message){
            super(message);
        }
    }

    public static class NotRegisteredException extends Exception{
        public NotRegisteredException(String message){
            super(message);
        }
    }

    public static class InvalidInputException extends Exception{
        public InvalidInputException(String message){
            super(message);
        }
    }

    public static class InvalidUsernameException extends InvalidInputException{
        public InvalidUsernameException(String message){
            super(message);
        }
    }

    public static class InvalidPasswordException extends InvalidInputException{
        public InvalidPasswordException(String message){
            super(message);
        }
    }

    public static class InvalidEmailException extends InvalidInputException{
        public InvalidEmailException(String message){
            super(message);
        }
    }

    public static class AlreadyRegisteredException extends Exception{
        public AlreadyRegisteredException(String message){
            super(message);
        }
    }
}
