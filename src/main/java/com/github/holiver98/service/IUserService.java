package com.github.holiver98.service;

import com.github.holiver98.model.User;

import java.util.Optional;

public interface IUserService{
    /**
     * Logs in the user, that is identified by the emailAddress and returns it.
     *
     * @param emailAddress The email address, that identifies the user.
     * @param password The password of the user.
     * @throws NullPointerException if the password or the emailAddress is null.
     * @throws IncorrectPasswordException if the given password does not match.
     * @throws NotFoundException if the given email address is not registered.
     *
     * @return the logged in user.
     */
    User login(String emailAddress, String password) throws IncorrectPasswordException, NotFoundException;

    /**
     * Logs the user out.
     *
     * @param emailAddress the email address, that identifies the user.
     * @return 0 on failure, 1 on success.
     */
    int logout(String emailAddress);

    /**
     * @param user The user we want to register. Contains the user information.
     * @throws NullPointerException if the user is null.
     * @throws IllegalArgumentException if the user information are invalid.
     * @throws AlreadyExistsException if the email address of the user is already registered.
     */
    void register(User user) throws AlreadyExistsException;

    /**
     * Gets the user identified by this emailAddress.
     *
     * @param emailAddress the user's email address.
     *
     * @return the user or null if, no user is registered with this email address.
     */
    Optional<User> getUser(String emailAddress);
}
