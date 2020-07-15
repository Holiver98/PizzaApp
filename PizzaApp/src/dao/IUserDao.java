package dao;

import model.User;

public interface IUserDao {
    long saveUser(User user);
    User getUserByEmailAddress(String emailAddress);
    void updateUser(User user);
    void deleteUser(User user);
}
