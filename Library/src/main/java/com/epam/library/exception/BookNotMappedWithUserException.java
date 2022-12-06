package com.epam.library.exception;

public class BookNotMappedWithUserException extends RuntimeException{
    public BookNotMappedWithUserException(String message){
        super(message);
    }

}
