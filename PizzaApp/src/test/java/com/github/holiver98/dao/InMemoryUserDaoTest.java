package com.github.holiver98.dao;

import com.github.holiver98.database.InMemoryDatabase;
import com.github.holiver98.model.User;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class InMemoryUserDaoTest {

    private InMemoryDatabase database;
    private IInMemoryUserDao userDao;

    @BeforeEach
    void init(){
        database = new InMemoryDatabase();
        userDao = new InMemoryUserDao(database);
    }

    @Test
    void getUserByEmailAddress_Db_Contains_User_Should_Return_User() {
        //Arrange
        User joe = new User();
        joe.setUsername("Joe");
        joe.setEmailAddress("test@something.com");
        joe.setPassword("123hello");
        database.users.add(joe);

        User bob = new User();
        bob.setUsername("Bob");
        bob.setEmailAddress("test5465@something.com");
        bob.setPassword("123gfhjhello");
        database.users.add(bob);

        User max = new User();
        max.setUsername("Max");
        max.setEmailAddress("anotherthing@something.com");
        max.setPassword("HHHdd3");
        database.users.add(max);

        //Act
        Optional<User> resultUser = userDao.getUserByEmailAddress("test5465@something.com");

        //Assert
        assertThat(resultUser.get()).isEqualTo(bob);
    }

    @Test
    void getUserByEmailAddress_Db_Doesnt_Contain_User_Should_Return_Null() {
        //Arrange
        User joe = new User();
        joe.setUsername("Joe");
        joe.setEmailAddress("test@something.com");
        joe.setPassword("123hello");
        database.users.add(joe);

        User bob = new User();
        bob.setUsername("Bob");
        bob.setEmailAddress("test5465@something.com");
        bob.setPassword("123gfhjhello");
        database.users.add(bob);

        User max = new User();
        max.setUsername("Max");
        max.setEmailAddress("anotherthing@something.com");
        max.setPassword("HHHdd3");
        database.users.add(max);

        //Act
        Optional<User> resultUser = userDao.getUserByEmailAddress("te11111115@something.com");

        //Assert
        assertThat(resultUser.isPresent()).isEqualTo(false);
    }

    @Test
    void getUserByEmailAddress_Passing_Null_Argument_Should_Return_Null() {
        //Arrange
        User joe = new User();
        joe.setUsername("Joe");
        joe.setEmailAddress("test@something.com");
        joe.setPassword("123hello");
        database.users.add(joe);

        User bob = new User();
        bob.setUsername("Bob");
        bob.setEmailAddress("test5465@something.com");
        bob.setPassword("123gfhjhello");
        database.users.add(bob);

        User max = new User();
        max.setUsername("Max");
        max.setEmailAddress("anotherthing@something.com");
        max.setPassword("HHHdd3");
        database.users.add(max);

        //Act
        Optional<User> resultUser = userDao.getUserByEmailAddress(null);

        //Assert
        assertThat(resultUser.isPresent()).isEqualTo(false);
    }

    @Test
    void saveUser_Saving_User_Should_Be_Inside_Database() {
        //Arrange
        User bob = new User();
        bob.setUsername("Bob");
        bob.setEmailAddress("test5465@something.com");
        bob.setPassword("123gfhjhello");
        database.users.add(bob);

        User joe = new User();
        joe.setUsername("Joe");
        joe.setEmailAddress("test@something.com");
        joe.setPassword("123hello");

        //Act
        userDao.saveUser(joe);

        //Assert
        assertThat(database.users).hasSize(2)
                .contains(joe, bob);
    }

    @Test
    void saveUser_Saving_User_Whos_Email_Is_Already_Registered_Should_Not_Save(){
        //Arrange
        User bob = new User();
        bob.setUsername("Bob");
        bob.setEmailAddress("test5465@something.com");
        bob.setPassword("123gfhjhello");
        database.users.add(bob);

        User joe = new User();
        joe.setUsername("Joe");
        joe.setEmailAddress("test@something.com");
        joe.setPassword("123hello");
        database.users.add(joe);

        User sam = new User();
        sam.setUsername("Sam");
        sam.setEmailAddress("test@something.com");
        sam.setPassword("777777");

        //Act
        userDao.saveUser(sam);

        //Assert
        assertThat(database.users).hasSize(2)
                .contains(joe, bob);
    }

    @Test
    void saveUser_Saving_User_That_Already_Exists_Should_Have_No_Duplicates() {
        //Arrange
        User joe = new User();
        joe.setUsername("Joe");
        joe.setEmailAddress("test@something.com");
        joe.setPassword("123hello");

        //Act
        userDao.saveUser(joe);
        userDao.saveUser(joe);

        //Assert
        assertThat(database.users).hasSize(1);
    }

    @Test
    void saveUser_Passing_Null_Argument_Should_Not_Change_Anything(){
        //Arrange
        User joe = new User();
        joe.setUsername("Joe");
        joe.setEmailAddress("test@something.com");
        joe.setPassword("123hello");
        database.users.add(joe);

        User bob = new User();
        bob.setUsername("Bob");
        bob.setEmailAddress("test5465@something.com");
        bob.setPassword("123gfhjhello");
        database.users.add(bob);

        User max = new User();
        max.setUsername("Max");
        max.setEmailAddress("anotherthing@something.com");
        max.setPassword("HHHdd3");
        database.users.add(max);

        //Act
        userDao.saveUser(null);

        //Assert
        assertThat(database.users).hasSize(3)
            .contains(max, joe, bob);
    }

    @Test
    void deleteUser_Deleting_User_Should_Be_Deleted(){
        //Arrange
        User joe = new User();
        joe.setUsername("Joe");
        joe.setEmailAddress("test@something.com");
        joe.setPassword("123hello");
        database.users.add(joe);

        User bob = new User();
        bob.setUsername("Bob");
        bob.setEmailAddress("test5465@something.com");
        bob.setPassword("123gfhjhello");
        database.users.add(bob);

        User max = new User();
        max.setUsername("Max");
        max.setEmailAddress("anotherthing@something.com");
        max.setPassword("HHHdd3");
        database.users.add(max);

        //Act
        userDao.deleteUser(bob.getEmailAddress());

        //Assert
        assertThat(database.users).hasSize(2)
                .contains(max, joe);
    }

    @Test
    void deleteUser_Passing_Null_Argument_Should_Not_Change_Anything(){
        //Arrange
        User joe = new User();
        joe.setUsername("Joe");
        joe.setEmailAddress("test@something.com");
        joe.setPassword("123hello");
        database.users.add(joe);

        User bob = new User();
        bob.setUsername("Bob");
        bob.setEmailAddress("test5465@something.com");
        bob.setPassword("123gfhjhello");
        database.users.add(bob);

        User max = new User();
        max.setUsername("Max");
        max.setEmailAddress("anotherthing@something.com");
        max.setPassword("HHHdd3");
        database.users.add(max);

        //Act
        userDao.deleteUser(null);

        //Assert
        assertThat(database.users).hasSize(3)
                .contains(max, joe, bob);
    }

    @Test
    void updateUser_Updating_User_Should_Be_Updated(){
        //Arrange
        User joe = new User();
        joe.setUsername("Joe");
        joe.setEmailAddress("test@something.com");
        joe.setPassword("123hello");
        database.users.add(joe);

        User bob = new User();
        bob.setUsername("Bob");
        bob.setEmailAddress("123@test.hu");
        bob.setPassword("123gfhjhello");
        database.users.add(bob);

        User max = new User();
        max.setUsername("Max");
        max.setEmailAddress("anotherthing@something.com");
        max.setPassword("HHHdd3");
        database.users.add(max);

        User newBob = new User();
        newBob.setUsername("Bobby");
        newBob.setEmailAddress("123@test.hu");
        newBob.setPassword("ddddasg44");

        //Act
        userDao.updateUser(newBob);

        //Assert
        assertThat(database.users).hasSize(3);
        assertThat(bob).isEqualTo(newBob);
    }

    @Test
    void updateUser_Passing_Null_Argument_Should_Not_Change_Anything(){
        //Arrange
        User max = new User();
        max.setUsername("Max");
        max.setEmailAddress("anotherthing@something.com");
        max.setPassword("HHHdd3");
        database.users.add(max);

        User maxInitial = new User();
        maxInitial.setUsername("Max");
        maxInitial.setEmailAddress("anotherthing@something.com");
        maxInitial.setPassword("HHHdd3");

        //Act
        userDao.updateUser(null);

        //Assert
        assertThat(database.users).hasSize(1);
        assertThat(max).isEqualTo(maxInitial);
    }
}
