package service;

import model.User;

public interface IUserService {
    void login(String emailAddress, String password);
    void logout();
    void register(User user);
    User getLoggedInUser();
}