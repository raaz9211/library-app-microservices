package com.epam.library.exception;

public class BookNotReleasedWithUserException extends RuntimeException{
    public BookNotReleasedWithUserException(String message){
        super(message);
    }

}
