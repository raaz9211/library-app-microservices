package com.epam.books.exception;

public class BookNotRemovedException extends RuntimeException {
    public BookNotRemovedException(String message) {
        super(message);
    }
}
