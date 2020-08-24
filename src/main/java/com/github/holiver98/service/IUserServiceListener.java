package com.github.holiver98.service;

import com.github.holiver98.model.User;

public interface IUserServiceListener {
    /**
     * Called after a successful login.
     *
     * @param user The user, that logged in.
     */
    void OnLoggedIn(User user);

    /**
     * Called after a successful logout.
     */
    void OnLoggedOut();
}
