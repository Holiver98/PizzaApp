package dao;

import model.User;

public interface IUserDao {
    /**
     * Saves the user into database.
     * It does not return the generated id, because the email address already
     * identifies the user.
     *
     * @param user The user to be saved.
     */
    void saveUser(User user);

    /**
     * Gets the user by email address from the database.
     *
     * @param emailAddress The email address, that identifies the user.
     * @return The user identified by the given email address, or null, if it doesn't exist in the database.
     */
    User getUserByEmailAddress(String emailAddress);

    /**
     * Updates the user in the database, that has the same id, as the user argument.
     *
     * @param user The user to be updated.
     */
    void updateUser(User user);

    /**
     * Deletes the user with the given emailAddress from the database, if it exists.
     *
     * @param emailAddress The email address of the user.
     */
    void deleteUser(String emailAddress);
}
