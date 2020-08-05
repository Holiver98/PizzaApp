package com.github.holiver98.service.jpa;

import com.github.holiver98.model.User;
import com.github.holiver98.dal.jpa.IUserRepository;
import com.github.holiver98.service.UserServiceBase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class JpaUserService extends UserServiceBase {
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected Optional<User> findByEmailAddress(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress);
    }

    @Override
    protected void save(User user) {
        userRepository.save(user);
    }
}
