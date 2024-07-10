package com.revature;

import com.revature.controller.UserController;
import com.revature.repository.SqliteUserDao;
import com.revature.repository.UserDao;
import com.revature.service.UserService;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)){


            UserDao userDao = new SqliteUserDao();

            UserService userService = new UserService(userDao);

            UserController userController = new UserController(scanner, userService);

            Map<String, String> controlMap = new HashMap<>();
            controlMap.put("Continue Loop", "true");
            while(Boolean.parseBoolean(controlMap.get("Continue Loop"))){

                userController.promptUserForService(controlMap);
                if(controlMap.containsKey("User")){
                    System.out.printf("Banking stuff for %s can happen here! Press any key to continue", controlMap.get("User"));
                    scanner.nextLine();

                }
            }
        }
    }
}