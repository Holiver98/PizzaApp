package com.github.holiver98.service;

import com.github.holiver98.dao.IInMemoryUserDao;
import com.github.holiver98.model.User;
import com.github.holiver98.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class JpaUserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    private User loggedInUser;

    @Override
    public void login(String emailAddress, String password) {
        if(password == null || emailAddress == null){
            return;
        }

        if(loggedInUser != null){
            System.out.println("Already logged in!");
            return;
        }

        Optional<User> userResult = userRepository.findByEmailAddress(emailAddress);
        User user;

        if(!userResult.isPresent()){
            System.out.println("User not registered!");
            return;
        }else{
            user = userResult.get();
        }

        if(!password.equals(user.getPassword())){
            System.out.println("Invalid password!");
            return;
        }

        loggedInUser = user;
    }

    @Override
    public void logout() {
        if(loggedInUser == null){
            System.out.println("Can't logout, because user is not logged in!");
            return;
        }

        loggedInUser = null;
    }

    @Override
    public void register(User user) {
        if(user == null)
        {
            return;
        }

        if(!isValidUserName(user.getUsername())) {
            System.out.println("Invalid username");
            return;
        }

        if(!isValidPassword(user.getPassword())) {
            System.out.println("Invalid password");
            return;
        }

        if(!isValidEmailAddress(user.getEmailAddress())) {
            System.out.println("Invalid email address");
            return;
        }

        if(isEmailRegistered(user.getEmailAddress())){
            System.out.println("Email already registered");
            return;
        }

        userRepository.save(user);
    }

    @Override
    public User getLoggedInUser() {
        return loggedInUser;
    }

    private boolean isValidUserName(String username) {
        if(username == null){
            return false;
        }else if(username.length() < 3){
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        if(password == null) {
            return false;
        } else if(password.length() < 5) {
            return false;
        }

        return true;
    }

    private boolean isValidEmailAddress(String emailAddress) {
        if(emailAddress == null) {
            return false;
        }

        String emailFormat = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if(emailAddress.matches(emailFormat)){
            return true;
        }else{
            return false;
        }
    }

    private boolean isEmailRegistered(String emailAddress){
        Optional<User> user = userRepository.findByEmailAddress(emailAddress);
        if(!user.isPresent()) {
            return false;
        }else{
            return true;
        }
    }
}
