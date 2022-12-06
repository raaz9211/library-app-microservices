package com.epam.users.exception;

public class UserNotRemovedException extends RuntimeException {
    public UserNotRemovedException(String message) {
        super(message);
    }
}
