package service;

import dao.IUserDao;
import model.User;

public class UserService implements IUserService {

    private IUserDao userDao;
    private User loggedInUser;

    public UserService(IUserDao dao)
    {
        userDao = dao;
    }

    @Override
    public void login(String emailAddress, String password) {
        if(password == null || emailAddress == null){
            return;
        }

        if(loggedInUser != null){
            System.out.println("Already logged in!");
            return;
        }

        User user = userDao.getUserByEmailAddress(emailAddress);

        if(user == null){
            System.out.println("User not registered!");
            return;
        }

        if(!password.equals(user.getPassword())){
            System.out.println("Invalid password!");
            return;
        }

        loggedInUser = user;
    }

    @Override
    public void logout() {
        if(loggedInUser == null){
            System.out.println("Can't logout, because user is not logged in!");
            return;
        }

        loggedInUser = null;
    }

    @Override
    public void register(User user) {
        if(user == null)
        {
            return;
        }

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
        return loggedInUser;
    }

    private boolean isValidUserName(String username) {
        if(username == null){
            return false;
        }else if(username.length() < 3){
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

        String emailFormat = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if(emailAddress.matches(emailFormat)){
            return true;
        }else{
            return false;
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
