package com.revature.controller;

import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.service.UserService;

import java.util.Map;
import java.util.Scanner;

public class UserController
{

    private Scanner scanner;
    private UserService userService;

    public UserController(Scanner scanner, UserService userService)
    {
        this.scanner = scanner;
        this.userService = userService;
    }

    public void promptUserForService(Map<String, String> controlMap)
    {

        System.out.println("What would you like to do?");
        System.out.println("1. register an account.");
        System.out.println("2. login.");
        System.out.println("q. quit.");

        try {
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "1":
                case "r":
                case "register":
                    registerNewUser();
                    break;
                case "2":
                case "l":
                case "login":
                    controlMap.put("User", login().getUsername());
                    break;
                case "q":
                case "quit":
                    System.out.println("Goodbye!");
                    controlMap.put("Program Looping", "False");
            }

        }catch(LoginFail exception)
        {
            System.out.println(exception.getMessage());
        }

    }

    public void registerNewUser()
    {
        String newUsername;
        String newPassword;
        System.out.println("Please Enter a Username.");
        newUsername = scanner.nextLine();
        System.out.println("Please Enter a Password.");
        newPassword = scanner.nextLine();

        User newCredentials = new User(newUsername, newPassword, false);
        User newUser = userService.validateUserCredentials(newCredentials);
        System.out.printf("New account created: %s", newUser);
    }

    public User login()
    {
        return userService.checkLoginCredentials(getUserCredentials());
    }

    public User getUserCredentials()
    {
        String newUsername;
        String newPassword;
        System.out.println("Please Enter a Username.");
        newUsername = scanner.nextLine();
        System.out.println("Please Enter a Password.");
        newPassword = scanner.nextLine();

        return new User(newUsername, newPassword, false);
    }


}
