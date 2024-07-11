package com.revature.controller;

import com.revature.entity.BankAccount;
import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.service.BankService;
import com.revature.service.UserService;

import java.util.Map;
import java.util.Scanner;

public class UserController
{

    private Scanner scanner;
    private UserService userService;
    private BankService bankService;


    public UserController(Scanner scanner, UserService userService, BankService bankService)
    {
        this.scanner = scanner;
        this.userService = userService;
        this.bankService = bankService;
    }

    public void promptUserForService(Map<String, String> controlMap)
    {

        System.out.println("What would you like to do?");
        System.out.println("1. register an account.");
        System.out.println("2. login.");
        System.out.println("3. Create an account.");
        System.out.println("4. Deposit money.");
        System.out.println("5. Withdraw money.");
        System.out.println("6. Close an account.");
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
                case "3":
                case "c":
                case "create":
                    createAccount();
                    break;
                case "4":
                case "d":
                case "deposit":
                    deposit();
                    break;
                case "5":
                case "w":
                case "withdraw":
                    withdraw();
                    break;
                case "6":
                case "cl":
                case "close":
                    closeAccount();
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

    public BankAccount createAccount()
    {
        System.out.println("Make a deposit in order to create your account.");
        System.out.println("How much would you like to deposit?");
        double deposit = Double.parseDouble(scanner.nextLine());

        return bankService.openAccount(getUserCredentials(), deposit);
    }

    public void closeAccount(BankAccount bankAccountInfo, Map<String, String> controlMap) {
            System.out.println("Are you sure you would like to close your account.");
            System.out.println("Type y/n?");
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "y":
                case "Y":
                    bankService.closeAccount(bankAccountInfo);
                    break;
                case "n":
                case "N":
                    controlMap.put("Program Looping", "True");
                    break;
            }
        }


        public BankAccount withdraw (BankAccount bankAccountInfo)
        {
            if (bankAccountInfo.getUser().hasAccount()) {
                System.out.println("Your current balance is: " + bankAccountInfo.getBalance());
                System.out.println("How much would you like to withdraw?");
                double withdrawal = Double.parseDouble(scanner.nextLine());

                bankAccountInfo.setBalance(bankAccountInfo.getBalance() - withdrawal);
                bankService.updateBalance(bankAccountInfo);

                return bankAccountInfo;
            }
            throw new NullPointerException("You must first create an account.");
        }

        public BankAccount deposit (BankAccount bankAccountInfo)
        {
            if (bankAccountInfo.getUser().hasAccount()) {
                System.out.println("Your current balance is: " + bankAccountInfo.getBalance());
                System.out.println("How much would you like to deposit?");
                double deposit = Double.parseDouble(scanner.nextLine());

                bankAccountInfo.setBalance(bankAccountInfo.getBalance() + deposit);
                bankService.updateBalance(bankAccountInfo);

                return bankAccountInfo;
            }
            throw new NullPointerException("You must first create an account.");
        }
}
