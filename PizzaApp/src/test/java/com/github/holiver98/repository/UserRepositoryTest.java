package com.github.holiver98.repository;

import com.github.holiver98.model.User;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.config.BootstrapMode;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    IUserRepository repo;

    @Test
    void test1(){
        User u = new User();
        u.setPassword("123123");
        u.setUsername("bobby");
        u.setEmailAddress("dsadas@fds.hu");

        User savedUser = repo.save(u);
        System.out.println(savedUser);

        User u2 = new User();
        u2.setPassword("13");
        u2.setUsername("dude");
        u2.setEmailAddress("dsadas@fds.hu");

        User savedUser2 = repo.save(u2);
        System.out.println(savedUser2);

        System.out.println("******Users:");
        for (User user: repo.findAll()) {
            System.out.println(user);
        }
    }
}
