package com.epam.users.exception;

public class UserNotSavedException extends RuntimeException {
    public UserNotSavedException(String message) {
        super(message);
    }
}
