package dao;

import database.InMemoryDatabase;
import model.User;
import java.util.Objects;

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

        for (User dbUser : dbContext.users){
            if(Objects.equals(dbUser.getEmailAddress(), user.getEmailAddress()))
            {
                //Email already registered
                return;
            }
        }

        dbContext.users.add(user);
    }

    @Override
    public User getUserByEmailAddress(String emailAddress) {
        for (User dbUser : dbContext.users) {
            if(Objects.equals(dbUser.getEmailAddress(), emailAddress)){
                return dbUser;
            }
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        if(user == null)
        {
            return;
        }

        for (User dbUser : dbContext.users) {
            if(Objects.equals(dbUser.getEmailAddress(), user.getEmailAddress())){
                dbUser.setUsername(user.getUsername());
                dbUser.setPassword(user.getPassword());
                return;
            }
        }
    }

    @Override
    public void deleteUser(String emailAddress) {
        for (User user : dbContext.users) {
            if(user.getEmailAddress().equals(emailAddress)){
                dbContext.users.remove(user);
                return;
            }
        }
    }
}
