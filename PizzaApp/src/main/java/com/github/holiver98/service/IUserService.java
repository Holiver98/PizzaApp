package com.github.holiver98.service;

import com.github.holiver98.model.User;

public interface IUserService {
    /**
     * Logs in the user, that is identified by the emailAddress, if the user is
     * already registered and the password matches.
     *
     * @param emailAddress The email address, that identifies the user.
     * @param password The password of the user.
     */
    void login(String emailAddress, String password) throws IncorrectPasswordException, NotRegisteredException;

    /**
     * Logs the currently logged in user out.
     */
    void logout();

    /**
     * Registers the given user, if it's email address isn't already registered.
     *
     * @param user The user we want to register. Contains the user information.
     */
    void register(User user) throws AlreadyExistsException;

    /**
     * Gets the currently logged in user.
     *
     * @return The currently logged in user, or null, if no user is logged in.
     */
    User getLoggedInUser();
}
