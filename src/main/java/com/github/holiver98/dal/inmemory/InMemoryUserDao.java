package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserDao implements IInMemoryUserDao {
    private List<User> users = new ArrayList<User>();

    public List<User> getUsers(){
        return users;
    }

    @Override
    public Optional<User> saveUser(User user) {
        if(user == null) {
            throw new NullPointerException("user is null");
        }

        Optional<User> dbUser = getUserByEmailAddress(user.getEmailAddress());
        if(dbUser.isPresent()){
            //Email already registered
            return Optional.empty();
        }

        users.add(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> getUserByEmailAddress(String emailAddress) {
        if(emailAddress == null){
            throw new NullPointerException("emailAddress is null");
        }
                return users.stream()
                .filter(u -> u.getEmailAddress().equals(emailAddress))
                .findFirst();
    }

    @Override
    public int updateUser(User user) {
        if(user == null) {
            throw new NullPointerException("user is null");
        }

        Optional<User> oldUser = getUserByEmailAddress(user.getEmailAddress());
        if(oldUser.isPresent()){
            updateOldUser(oldUser.get(), user);
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public int deleteUser(String emailAddress) {
        if(emailAddress == null){
            throw new NullPointerException("emailAddress is null");
        }
        Optional<User> user = getUserByEmailAddress(emailAddress);
        if(user.isPresent()){
            users.remove(user.get());
            return 1;
        }else{
            return -1;
        }
    }

    private void updateOldUser(User oldUser, User newUser){
        oldUser.setEmailAddress(newUser.getEmailAddress());
        oldUser.setRole(newUser.getRole());
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(newUser.getPassword());
    }
}
