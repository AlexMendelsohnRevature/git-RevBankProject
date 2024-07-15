package com.revature.repository;

import com.revature.entity.User;

import java.util.List;

public interface UserDao {

    User createUser(User newUserCredentials);
    User getUser(int id);
    User updateUser(User userCredentials);
    int getUserID(String username, String password);
    List<User> getAllUsers();

}
