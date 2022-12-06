package com.epam.books.exception;

public class BookNotSavedException extends RuntimeException {
    public BookNotSavedException(String message) {
        super(message);
    }
}
