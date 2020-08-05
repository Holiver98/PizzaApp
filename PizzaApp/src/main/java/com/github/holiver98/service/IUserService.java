package com.github.holiver98.service;

import com.github.holiver98.model.User;

public interface IUserService {
    /**
     * Logs in the user, that is identified by the emailAddress.
     *
     * @param emailAddress The email address, that identifies the user.
     * @param password The password of the user.
     * @throws NullPointerException if the password or the emailAddress is null.
     * @throws IncorrectPasswordException if the given password does not match.
     * @throws NotFoundException if the given email address is not registered.
     * @throws UnsupportedOperationException if the user is already logged in.
     */
    void login(String emailAddress, String password) throws IncorrectPasswordException, NotFoundException;

    /**
     * Logs the currently logged in user out.
     */
    void logout();

    /**
     * Registers the given user.
     *
     * @param user The user we want to register. Contains the user information.
     * @throws NullPointerException if the user is null.
     * @throws IllegalArgumentException if the user information are invalid.
     * @throws AlreadyExistsException if the email address of the user is already registered.
     */
    void register(User user) throws AlreadyExistsException;

    /**
     * Gets the currently logged in user.
     *
     * @return The currently logged in user, or null, if no user is logged in.
     */
    User getLoggedInUser();
}
