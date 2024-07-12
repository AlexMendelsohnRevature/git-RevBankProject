package com.revature.service;

import com.revature.entity.User;
import com.revature.exception.LoginFail;
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
        }
        throw new RuntimeException("An account with that name exists or invalid account name.");
    }

    public User checkLoginCredentials(User credentials)
    {
        for(User user : userDao.getAllUsers())
        {
            boolean usernameMatches = user.getUsername().equals(credentials.getUsername());
            boolean passwordMatches = user.getPassword().equals(credentials.getPassword());
            boolean loggedIn = user.isLoggedIn();
            if(usernameMatches && passwordMatches && !loggedIn)
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
        User authUser = userDao.getUser(userDao.getUserID(user.getUsername(), user.getPassword()).getId());
        authUser.setLoggedIn(true);
        return authUser;
    }
}
