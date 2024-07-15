package com.revature.exception;

public class ValidateUserException extends RuntimeException {

    public ValidateUserException(String message)
    {
        System.out.println(message);
    }
}
