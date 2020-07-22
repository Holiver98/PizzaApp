package com.github.holiver98.dao;

import com.github.holiver98.database.InMemoryDatabase;
import com.github.holiver98.model.User;
import java.util.Optional;

public class InMemoryUserDao implements IUserDao{
    private InMemoryDatabase dbContext;

    public InMemoryUserDao(InMemoryDatabase context) {
        dbContext = context;
    }

    @Override
    public void saveUser(User user) {
        if(user == null)
        {
            return;
        }

        Optional<User> dbUser = getUserByEmailAddress(user.getEmailAddress());
        if(dbUser.isPresent()){
            //Email already registered
            return;
        }

        dbContext.users.add(user);
    }

    @Override
    public Optional<User> getUserByEmailAddress(String emailAddress) {
                return dbContext.users.stream()
                .filter(u -> u.getEmailAddress().equals(emailAddress))
                .findFirst();
    }

    @Override
    public void updateUser(User user) {
        if(user == null)
        {
            return;
        }

        Optional<User> oldUser = getUserByEmailAddress(user.getEmailAddress());
        oldUser.ifPresent(o -> updateOldUser(o, user));
    }

    @Override
    public void deleteUser(String emailAddress) {
        Optional<User> user = getUserByEmailAddress(emailAddress);
        user.ifPresent(u -> dbContext.users.remove(u));
    }

    private void updateOldUser(User oldUser, User newUser){
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(newUser.getPassword());
    }
}
