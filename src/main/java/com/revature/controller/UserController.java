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
        System.out.println("1. Register an account.");
        System.out.println("2. Login.");
        System.out.println("q. Quit.");

        try {
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "1":
                case "r":
                case "R":
                case "register":
                case "Register":
                    registerNewUser();
                    break;
                case "2":
                case "l":
                case "L":
                case "login":
                case "Login":
                    controlMap.put("User", login().getUsername());
                    promptUserForOptions();
                    try {
                        String newUserActionIndicated = scanner.nextLine();
                        switch (newUserActionIndicated) {
                            case "1":
                            case "d":
                            case "D":
                            case "deposit":
                            case "Deposit":
                                deposit(authAccount());
                                break;
                            case "2":
                            case "w":
                            case "W":
                            case "withdraw":
                            case "Withdraw":
                                withdraw(authAccount());
                                break;
                            case "3":
                            case "c":
                            case "C":
                            case "close":
                            case "Close":
                                closeAccount(authAccount(), controlMap);
                                break;
                            case "4":
                            case "q":
                            case "Q":
                            case "quit":
                            case "Quit":
                                System.out.println("Goodbye!");
                                controlMap.put("Continue Loop", "false");
                        }
                    }catch(LoginFail exception)
                    {
                        System.out.println(exception.getMessage());
                    }
                    break;
                case "3":
                case "q":
                case "Q":
                case "quit":
                case "Quit":
                    System.out.println("Goodbye!");
                    controlMap.put("Continue Loop", "false");
            }

        }catch(LoginFail exception)
        {
            System.out.println(exception.getMessage());
        }

    }
    public void promptUserForOptions()
    {
        System.out.println("What would you like to do?");
        System.out.println("1. Deposit money.");
        System.out.println("2. Withdraw money.");
        System.out.println("3. Close an account.");
        System.out.println("q. Quit.");
    }

    public void registerNewUser()
    {
        String newUsername;
        String newPassword;
        System.out.println("Please Enter a Username.");
        newUsername = scanner.nextLine();
        System.out.println("Please Enter a Password.");
        newPassword = scanner.nextLine();

        User newCredentials = new User(newUsername, newPassword);
        User newUser = userService.validateUserCredentials(newCredentials);

        BankAccount newAccount = createAccount(newUser);

        System.out.printf("New account created: %s", newUser + " With a starting Balance of: " + newAccount.getBalance() + "\n");
    }

    public User login()
    {
        return userService.checkLoginCredentials(getUserCredentials());
    }


    public BankAccount authAccount()
    {
        return bankService.getAccount();
    }
    public User getUserCredentials()
    {
        String newUsername;
        String newPassword;
        System.out.println("Please Enter a Username.");
        newUsername = scanner.nextLine();
        System.out.println("Please Enter a Password.");
        newPassword = scanner.nextLine();

        User user = new User(newUsername, newPassword);
        User authUser = userService.authenticatedUser(user);
        return userService.modifyUser(authUser);
    }

    public BankAccount createAccount(User user)
    {
        System.out.println("Make a deposit in order to create your account.");
        System.out.println("How much would you like to deposit?");
        double deposit = Double.parseDouble(scanner.nextLine());
        return bankService.openAccount(user, deposit);
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
                System.out.println("Your current balance is: " + bankAccountInfo.getBalance());
                System.out.println("How much would you like to withdraw?");
                double withdrawal = Double.parseDouble(scanner.nextLine());

                if(bankAccountInfo.getBalance() - withdrawal > 0) {
                    bankAccountInfo.setBalance(bankAccountInfo.getBalance() - withdrawal);
                }
                else
                {
                    System.out.println("Cannot drop balance below $0.");
                }
                bankService.updateBalance(bankAccountInfo);

                return bankAccountInfo;
        }

        public BankAccount deposit (BankAccount bankAccountInfo)
        {
                System.out.println("Your current balance is: " + bankAccountInfo.getBalance());
                System.out.println("How much would you like to deposit?");
                double deposit = Double.parseDouble(scanner.nextLine());

                bankAccountInfo.setBalance(bankAccountInfo.getBalance() + deposit);
                bankService.updateBalance(bankAccountInfo);

                return bankAccountInfo;
        }
}
