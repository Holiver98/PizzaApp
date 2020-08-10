package com.github.holiver98.service;

import com.github.holiver98.model.User;

import java.util.Optional;

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
     * @throws NullPointerException if emailAddress is null.
     * @throws NotFoundException if the emailAddress isn't registered.
     * @throws UnsupportedOperationException if the user isn't logged in.
     */
    void logout(String emailAddress) throws NotFoundException;

    /**
     * @param user The user we want to register. Contains the user information.
     * @throws NullPointerException if the user is null.
     * @throws IllegalArgumentException if the user information are invalid.
     * @throws AlreadyExistsException if the email address of the user is already registered.
     */
    void register(User user) throws AlreadyExistsException;

    /**
     * @return The logged in user (identified by emailAddress) or null if the user is not logged in.
     * @throws NotFoundException if the given email address is not registered.
     * @throws NullPointerException if emailAddress is null.
     */
    Optional<User> getLoggedInUser(String emailAddress) throws NotFoundException;
}
