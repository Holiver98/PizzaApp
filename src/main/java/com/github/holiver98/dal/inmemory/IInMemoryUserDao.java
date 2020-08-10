package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.User;

import java.util.Optional;

public interface IInMemoryUserDao {
    /**
     * @return The saved user. If user already exists, null is returned.
     * @throws NullPointerException if user is null.
     */
    Optional<User> saveUser(User user);

    /**
     * @param emailAddress The email address, that identifies the user.
     * @return The user identified by the given email address, or null, if it doesn't exist.
     * @throws NullPointerException if emailAddress is null.
     */
    Optional<User> getUserByEmailAddress(String emailAddress);

    /**
     * Updates the user, that has the same id, as the given user argument, with
     * the values of the user argument.
     *
     * @param user The user to be updated, with the new values.
     * @return 1 - success, -1 - user doesn't exist.
     * @throws NullPointerException if user is null.
     */
    int updateUser(User user);

    /**
     * Deletes the user.
     *
     * @param emailAddress The email address of the user to be deleted.
     * @return 1 - success, -1 - user doesn't exist with this emailAddress.
     * @throws NullPointerException if emailAddress is null.
     */
    int deleteUser(String emailAddress);
}
