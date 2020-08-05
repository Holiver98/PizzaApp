package com.github.holiver98.service.inmemory;

import com.github.holiver98.dal.inmemory.IInMemoryUserDao;
import com.github.holiver98.model.User;
import com.github.holiver98.service.IUserService;
import com.github.holiver98.service.UserServiceBase;

import java.util.Optional;

public class InMemoryUserService extends UserServiceBase {

    private IInMemoryUserDao userDao;

    public InMemoryUserService(IInMemoryUserDao dao)
    {
        userDao = dao;
    }

    @Override
    protected Optional<User> findByEmailAddress(String emailAddress) {
        return userDao.getUserByEmailAddress(emailAddress);
    }

    @Override
    protected void save(User user) {
        userDao.saveUser(user);
    }
}
