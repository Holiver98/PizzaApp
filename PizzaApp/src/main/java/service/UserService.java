package service;

import dao.IUserDao;
import model.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UserService implements IUserService {

    private IUserDao userDao;

    public UserService(IUserDao dao)
    {
        userDao = dao;
    }

    @Override
    public void login(String emailAddress, String password) {
        throw new NotImplementedException();
    }

    @Override
    public void logout() {
        throw new NotImplementedException();
    }

    @Override
    public void register(User user) {
        if(!isValidUserName(user.getUsername())) {
            System.out.println("Invalid username");
            return;
        }

        if(!isValidPassword(user.getPassword())) {
            System.out.println("Invalid password");
            return;
        }

        if(!isValidEmailAddress(user.getEmailAddress())) {
            System.out.println("Invalid email address");
            return;
        }

        if(isEmailRegistered(user.getEmailAddress())){
            System.out.println("Email already registered");
            return;
        }

        userDao.saveUser(user);
    }

    @Override
    public User getLoggedInUser() {
        throw new NotImplementedException();
    }

    private boolean isValidUserName(String username) {
        if(username == null){
            return false;
        }else if(username.length() < 5){
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        if(password == null) {
            return false;
        } else if(password.length() < 5) {
            return false;
        }

        return true;
    }

    private boolean isValidEmailAddress(String emailAddress) {
        if(emailAddress == null) {
            return false;
        }

        if(!emailAddress.contains("@")){
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isEmailRegistered(String emailAddress){
        User user = userDao.getUserByEmailAddress(emailAddress);
        if(user == null) {
            return false;
        }else{
            return true;
        }
    }
}
