package com.revature.controller;

import com.revature.entity.BankAccount;
import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.service.BankService;
import com.revature.service.UserService;

import java.util.List;
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
        System.out.println("1. Register an new user.");
        System.out.println("2. Log In.");
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
                    login(controlMap);
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

    public void promptUserForAccountOptions(Map<String, String> controlMap)
    {
        System.out.println("What would you like to do?");
        System.out.println("1. Create a checking account.");
        System.out.println("2. View your checking account/'s.");
        System.out.println("3. Close a checking account.");
        System.out.println("4. Log Out.");
        System.out.println("q. Quit.");
        try {
            String newUserActionIndicated = scanner.nextLine();
            switch (newUserActionIndicated) {
                case "1":
                case "c":
                case "C":
                case "create":
                case "Create":
                    createAccount(Integer.parseInt(controlMap.get("UserID")), controlMap);
                    break;
                case "2":
                case "v":
                case "V":
                case "view":
                case "View":
                    viewAccounts(Integer.parseInt(controlMap.get("UserID")), controlMap);
                    break;
                case "3":
                case "cl":
                case "Cl":
                case "close":
                case "Close":
                    closeAccount(controlMap);
                    break;
                case "4":
                case "l":
                case "L":
                case "logout":
                case "Logout":
                    logout(controlMap);
                    break;
                case "5":
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

    public void promptUserForOptions(Map<String, String> controlMap)
    {
        System.out.println("What would you like to do?");
        System.out.println("1. Deposit money.");
        System.out.println("2. Withdraw money.");
        System.out.println("3. Return.");
        try {
            String newUserActionIndicated = scanner.nextLine();
            switch (newUserActionIndicated) {
                case "1":
                case "d":
                case "D":
                case "deposit":
                case "Deposit":
                    System.out.println("Type the id of the account you wish to make a deposit in.");
                    System.out.println("If unsure type 0 to return to the previous selection.");
                    int id = Integer.parseInt(scanner.nextLine());
                    if(id == 0)
                    {
                        controlMap.put("Account", "true");
                    }else {
                        deposit(id);
                    }
                    break;
                case "2":
                case "w":
                case "W":
                case "withdraw":
                case "Withdraw":
                    System.out.println("Type the id of the account you wish to make a deposit in.");
                    System.out.println("If unsure type 0 to return to the previous selection.");
                    int id2 = Integer.parseInt(scanner.nextLine());
                    if(id2 == 0)
                    {
                        controlMap.put("Account", "true");
                    }else {
                        withdraw(id2);
                    }
                case "3":
                case "r":
                case "R":
                case "return":
                case "Return":
                    controlMap.put("Continue Loop", "true");
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

        User newCredentials = new User(newUsername, newPassword);
        User newUser = userService.validateUserCredentials(newCredentials);

        System.out.println("New user account created: " + newUser.toString());
    }

    public void login(Map<String, String> controlMap)
    {
        User user = userService.checkLoginCredentials(getUserCredentials());
        controlMap.put("UserID", Integer.toString(user.getId()));
        controlMap.put("Username", user.getUsername());
        controlMap.put("LoggedIn", "true");
        controlMap.put("User", user.toString());
    }

    public void logout(Map<String, String> controlMap)
    {
        User user = new User();
        user.setLoggedIn(false);
        controlMap.put("LoggedOut", "true");
        System.out.println("You are now logged out.");
        controlMap.put("Continue Loop", "true");
    }

    public List<BankAccount> getUsersAccounts(int userid)
    {
        return bankService.getAccounts(userid);
    }

    public void viewAccounts(int userid, Map<String, String> controlMap)
    {
        List<BankAccount> accounts = getUsersAccounts(userid);

        BankAccount nAccount = new BankAccount();
        for(BankAccount account  : accounts)
        {
            System.out.println("Your Account username is: " + account.getUsername());
            System.out.println("Your Account id is: (" + account.getId() + ")");
            System.out.println("Your Account balance is currently: $" + account.getBalance());
            nAccount = new BankAccount(account.getUserID(), account.getBalance(), account.getUsername());
        }
        controlMap.put("Account", nAccount.toString());
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

    public void createAccount(int userid, Map<String, String> controlMap)
    {
        System.out.println("Make a deposit in order to create your account.");
        System.out.println("How much would you like to deposit?");
        double deposit = Double.parseDouble(scanner.nextLine());
        BankAccount newBankAccount = bankService.openAccount(userid, deposit, controlMap.get("Username"));
        controlMap.put("id", Integer.toString(newBankAccount.getId()));
        System.out.println("New bank account created: " + newBankAccount);
    }

    public void closeAccount(Map<String, String> controlMap) {
            System.out.println("Are you sure you would like to close your account.");
            System.out.println("Type y/n?");
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "y":
                case "Y":
                    System.out.println("Type the id of the account you wish to close.");
                    System.out.println("If unsure type 0 to return to the previous selection.");
                    int id = Integer.parseInt(scanner.nextLine());
                    if(id == 0)
                    {
                        controlMap.put("Account", "true");
                    }else {
                        bankService.closeAccount(id);
                    }
                    break;
                case "n":
                case "N":
                    controlMap.put("User", "true");
                    break;
            }
        }


        public void withdraw(int id)
        {
                System.out.println("How much would you like to withdraw?");
                double withdrawal = Double.parseDouble(scanner.nextLine());
                bankService.updateBalance(id, "w", withdrawal);

        }

        public void deposit(int id)
        {
                System.out.println("How much would you like to deposit?");
                double deposit = Double.parseDouble(scanner.nextLine());
                bankService.updateBalance(id, "d", deposit);
        }
}
