package dao;

import database.InMemoryDatabase;
import model.User;
import java.util.Objects;
import java.util.Optional;

public class UserDao implements IUserDao{
    private InMemoryDatabase dbContext;

    public UserDao(InMemoryDatabase context) {
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

    private User updateOldUser(User oldUser, User newUser){
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(newUser.getPassword());
        return oldUser;
    }

    @Override
    public void deleteUser(String emailAddress) {
        Optional<User> user = getUserByEmailAddress(emailAddress);
        user.ifPresent(u -> dbContext.users.remove(u));
    }
}
