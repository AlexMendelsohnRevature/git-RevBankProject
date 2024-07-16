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

    public User createUser(User newUserCredentials)
    {
        return userDao.createUser(newUserCredentials);
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

    public boolean checkUsernamePasswordLength(User newUserCredentials)
    {
        boolean usernameIsValid = newUserCredentials.getUsername().length() <= 30;
        boolean passwordIsValid = newUserCredentials.getPassword().length() <= 30;
        return usernameIsValid && passwordIsValid;
    }

    public boolean checkUsernameIsUnique(User newUserCredentials)
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
