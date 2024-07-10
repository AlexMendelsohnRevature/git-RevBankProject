package com.revature.repository;


import com.revature.entity.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUser implements UserDao {

    private List<User> users;

    public InMemoryUser() {
        users = new ArrayList<>();
        users.add(new User("admin","password", false));
    }

    @Override
    public User createUser(User newUserCredentials) {
        users.add(newUserCredentials);
        return newUserCredentials;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }


}
