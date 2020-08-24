package com.github.holiver98.service;

import com.github.holiver98.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.Optional;

public abstract class UserServiceBase extends UserServiceBaseExceptionHandler{
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12, new SecureRandom());
    private User loggedInUser;

    protected abstract Optional<User> findByEmailAddress(String emailAddress);
    protected abstract void save(User user);
    protected abstract void update(User user);

    @Override
    public Optional<User> getLoggedInUser(){
        return Optional.ofNullable(loggedInUser);
    }

    public BCryptPasswordEncoder getPasswordEncoder(){
        return passwordEncoder;
    }

    @Override
    public void login(String emailAddress, String password) throws IncorrectPasswordException, NotFoundException {
        super.login(emailAddress, password);
        User userInfo = tryGetUser(emailAddress);
        IfAlreadyLoggedInThrowException();
        checkIfPasswordMatches(password, userInfo.getPassword());
        loggedInUser = userInfo;
    }

    @Override
    public void logout(){
        loggedInUser = null;
    }

    @Override
    public void register(User user) throws AlreadyExistsException {
        super.register(user);
        checkIfUserInformationAreValid(user);
        checkIfAlreadyRegistered(user);
        encodeUserPassword(user);
        save(user);
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

    private void encodeUserPassword(User user) {
        String plainTextPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(plainTextPassword));
    }

    private void checkIfAlreadyRegistered(User user) throws AlreadyExistsException {
        if(isEmailRegistered(user.getEmailAddress())){
            throw new AlreadyExistsException("email already registered: " + user.getEmailAddress());
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

    private User tryGetUser(String emailAddress) throws NotFoundException {
        Optional<User> registeredUser = findByEmailAddress(emailAddress);
        if(!registeredUser.isPresent()){
            throw new NotFoundException("The email address (" + emailAddress + ") is not registered!");
        }else{
            return registeredUser.get();
        }
    }

    private void checkIfPasswordMatches(String password, String encodedPassword) throws IncorrectPasswordException {
        if(!passwordEncoder.matches(password, encodedPassword)){
            throw new IncorrectPasswordException("password incorrect: " + password);
        }
    }

    private void IfAlreadyLoggedInThrowException() {
        if(loggedInUser != null){
            throw new UnsupportedOperationException("Already logged in with an account!");
        }
    }
}
