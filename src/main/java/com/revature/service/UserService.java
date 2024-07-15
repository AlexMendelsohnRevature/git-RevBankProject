package com.revature.service;

import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.exception.ValidateUserException;
import com.revature.repository.UserDao;

import java.util.List;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User validateUserCredentials(User newUserCredentials)
    {
        if(checkUsernamePasswordLength(newUserCredentials))
        {
            if(checkUsernameIsUnique(newUserCredentials))
            {
                return userDao.createUser(newUserCredentials);
            }
            throw new ValidateUserException("Username is already taken.");
        }
        throw new RuntimeException("Username or Password invalid Must be less than 30 Characters.");
    }

    public User checkLoginCredentials(User credentials)
    {
        for(User user : userDao.getAllUsers())
        {
            boolean usernameMatches = user.getUsername().equals(credentials.getUsername());
            boolean passwordMatches = user.getPassword().equals(credentials.getPassword());
            if(usernameMatches && passwordMatches)
            {
                return credentials;
            }
        }
        throw new LoginFail("Credentials are invalid: please try again");
    }

    private boolean checkUsernamePasswordLength(User newUserCredentials)
    {
        boolean usernameIsValid = newUserCredentials.getUsername().length() <= 30;
        boolean passwordIsValid = newUserCredentials.getPassword().length() <= 30;
        return usernameIsValid && passwordIsValid;
    }

    private boolean checkUsernameIsUnique(User newUserCredentials)
    {
        boolean userNameIsUnique = true;
        List<User> users = userDao.getAllUsers();
        for(User user : users){
            if(newUserCredentials.getUsername().equals(user.getUsername()))
            {
                userNameIsUnique = false;
                break;
            }
        }

        return userNameIsUnique;
    }

    public User modifyUser(User credentials)
    {
        return userDao.updateUser(credentials);
    }

    public User authenticatedUser(User user){
        User authUser = userDao.getUser(userDao.getUserID(user.getUsername(), user.getPassword()));
        authUser.setLoggedIn(true);
        return authUser;
    }
}
