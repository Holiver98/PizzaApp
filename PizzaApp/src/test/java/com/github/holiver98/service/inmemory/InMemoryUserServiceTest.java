package com.github.holiver98.service.inmemory;

import com.github.holiver98.dal.inmemory.IInMemoryUserDao;
import com.github.holiver98.model.User;
import com.github.holiver98.service.inmemory.InMemoryUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InMemoryUserServiceTest {

    private IInMemoryUserDao userDao;
    private InMemoryUserService userService;

    @BeforeEach
    void init(){
        userDao = Mockito.mock(IInMemoryUserDao.class);
        userService = new InMemoryUserService(userDao);
    }

    @Test
    void register_unregistered_valid_user_should_call_dao_saveUser() {
        //Arrange
        User bob = new User();
        bob.setUsername("Bob");
        bob.setPassword("123hello");
        bob.setEmailAddress("bob123@test.hu");

        //Act
        userService.register(bob);

        //Assert
        Mockito.verify(userDao, Mockito.times(1)).saveUser(bob);
    }

    @Test
    void register_invalid_username_should_not_save_user(){
        //Arrange
        User bob = new User();
        bob.setUsername("D");
        bob.setPassword("123hello");
        bob.setEmailAddress("bob123@test.hu");

        //Act
        userService.register(bob);

        //Assert
        Mockito.verify(userDao, Mockito.times(0)).saveUser(bob);
    }

    @Test
    void register_invalid_password_should_not_save_user(){
        //Arrange
        User bob = new User();
        bob.setUsername("Bob");
        bob.setPassword("123");
        bob.setEmailAddress("bob123@test.hu");

        //Act
        userService.register(bob);

        //Assert
        Mockito.verify(userDao, Mockito.times(0)).saveUser(bob);
    }

    @Test
    void register_invalid_email_should_not_save_user(){
        //Arrange
        User bob = new User();
        bob.setUsername("Bob");
        bob.setPassword("123hello");
        bob.setEmailAddress("bob123hu");

        //Act
        userService.register(bob);

        //Assert
        Mockito.verify(userDao, Mockito.times(0)).saveUser(bob);
    }

    @Test
    void register_invalid_email_should_not_save_user_2(){
        //Arrange
        User bob = new User();
        bob.setUsername("Bob");
        bob.setPassword("123hello");
        bob.setEmailAddress("@@@@@@@@");

        //Act
        userService.register(bob);

        //Assert
        Mockito.verify(userDao, Mockito.times(0)).saveUser(bob);
    }

    @Test
    void register_already_registered_user_should_not_save_user(){
        //Arrange
        User bob = new User();
        bob.setUsername("Bob");
        bob.setPassword("123hello");
        bob.setEmailAddress("bob123@test.hu");

        Mockito.when(userDao.getUserByEmailAddress("bob123@test.hu")).thenReturn(java.util.Optional.of(bob));

        //Act
        userService.register(bob);

        //Assert
        Mockito.verify(userDao, Mockito.times(0)).saveUser(bob);
    }

    @Test
    void login_registered_user_should_log_user_in(){
        //Arrange
        User bob = new User();
        bob.setUsername("Bob");
        bob.setPassword("123hello");
        bob.setEmailAddress("bob123@test.hu");

        Mockito.when(userDao.getUserByEmailAddress("bob123@test.hu")).thenReturn(java.util.Optional.of(bob));

        //Act
        userService.login(bob.getEmailAddress(), bob.getPassword());
        User loggedInUser = userService.getLoggedInUser();

        //Assert
        assertThat(loggedInUser).isEqualTo(bob);
    }

    @Test
    void login_unregistered_user_should_not_log_user_in(){
        //Arrange
        User bob = new User();
        bob.setUsername("Bob");
        bob.setPassword("123hello");
        bob.setEmailAddress("bob123@test.hu");

        //Act
        userService.login(bob.getEmailAddress(), bob.getPassword());
        User loggedInUser = userService.getLoggedInUser();

        //Assert
        assertThat(loggedInUser).isNull();
    }

    @Test
    void login_user_password_null_should_not_log_user_in(){
        //Arrange
        User bob = new User();
        bob.setUsername("Bob");
        bob.setPassword("123hello");
        bob.setEmailAddress("bob123@test.hu");

        Mockito.lenient().when(userDao.getUserByEmailAddress("bob123@test.hu")).thenReturn(java.util.Optional.of(bob));

        //Act
        userService.login(bob.getEmailAddress(), null);
        User loggedInUser = userService.getLoggedInUser();

        //Assert
        assertThat(loggedInUser).isNull();
    }

    @Test
    void login_user_email_null_should_not_log_user_in(){
        //Arrange
        User bob = new User();
        bob.setUsername("Bob");
        bob.setPassword("123hello");
        bob.setEmailAddress("bob123@test.hu");

        Mockito.lenient().when(userDao.getUserByEmailAddress("bob123@test.hu")).thenReturn(java.util.Optional.of(bob));

        //Act
        userService.login(bob.getEmailAddress(), null);
        User loggedInUser = userService.getLoggedInUser();

        //Assert
        assertThat(loggedInUser).isNull();
    }
}