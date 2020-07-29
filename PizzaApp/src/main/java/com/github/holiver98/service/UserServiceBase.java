package com.github.holiver98.service;

import com.github.holiver98.model.User;
import java.util.Optional;

public abstract class UserServiceBase extends UserServiceBaseExceptionHandler{
    protected User loggedInUser;

    protected abstract Optional<User> findByEmailAddress(String emailAddress);
    protected abstract void save(User user);

    @Override
    public void login(String emailAddress, String password) throws IncorrectPasswordException, NotRegisteredException {
        super.login(emailAddress, password);
        checkIfAlreadyLoggedIn();
        User userInfo = getUserInfoForLogin(emailAddress);
        checkIfPasswordMatches(password, userInfo);
        loggedInUser = userInfo;
    }

    @Override
    public void logout() {
        loggedInUser = null;
    }

    @Override
    public void register(User user) throws AlreadyRegisteredException {
        super.register(user);
        checkIfUserInformationAreValid(user);
        checkIfAlreadyRegistered(user);
        save(user);
    }

    @Override
    public User getLoggedInUser() {
        return loggedInUser;
    }

    protected boolean isValidUserName(String username) {
        if(username == null){
            return false;
        }else if(username.length() < 3){
            return false;
        }
        return true;
    }

    protected boolean isValidPassword(String password) {
        if(password == null) {
            return false;
        } else if(password.length() < 5) {
            return false;
        }
        return true;
    }

    protected boolean isValidEmailAddress(String emailAddress) {
        if(emailAddress == null) {
            return false;
        }

        String emailFormat = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return emailAddress.matches(emailFormat);
    }

    protected boolean isEmailRegistered(String emailAddress){
        Optional<User> registeredUser = findByEmailAddress(emailAddress);
        return registeredUser.isPresent();
    }

    private void checkIfAlreadyRegistered(User user) throws AlreadyRegisteredException {
        if(isEmailRegistered(user.getEmailAddress())){
            throw new AlreadyRegisteredException("email already registered: " + user.getEmailAddress());
        }
    }

    private void checkIfUserInformationAreValid(User user){
        if(!isValidUserName(user.getUsername())) {
            throw new IllegalArgumentException("invalid username: " + user.getUsername());
        }

        if(!isValidPassword(user.getPassword())) {
            throw new IllegalArgumentException("invalid password: " + user.getPassword());
        }

        if(!isValidEmailAddress(user.getEmailAddress())) {
            throw new IllegalArgumentException("invalid email address: " + user.getEmailAddress());
        }
    }

    private void checkIfAlreadyLoggedIn() {
        if(loggedInUser != null){
            throw new UnsupportedOperationException("Already logged in!");
        }
    }

    private User getUserInfoForLogin(String emailAddress) throws NotRegisteredException {
        Optional<User> registeredUser = findByEmailAddress(emailAddress);
        if(!registeredUser.isPresent()){
            throw new NotRegisteredException("The email address (" + emailAddress + ") is not registered!");
        }else{
            return registeredUser.get();
        }
    }

    private void checkIfPasswordMatches(String password, User registeredUser) throws IncorrectPasswordException {
        if(!password.equals(registeredUser.getPassword())){
            throw new IncorrectPasswordException("password incorrect: " + password);
        }
    }
}
