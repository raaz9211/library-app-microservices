package com.epam.library.exception;

public class BookIssueLimitexceedException extends RuntimeException{
    public BookIssueLimitexceedException(String message){
        super(message);
    }

}
