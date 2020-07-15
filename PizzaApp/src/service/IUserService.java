package service;

import model.User;

public interface IUserService {
    void login(String emailAddress, String password);
    /**
     * Logs the currently logged in user out.
     */
    void logout();

    /**
     * Registers the given user, if it's email address isn't already registered.
     *
     * @param user The user we want to register. Contains the user information.
     */
    void register(User user);

    /**
     * Gets the currently logged in user.
     *
     * @return The currently logged in user, or null, if no user is logged in.
     */
    User getLoggedInUser();
}
