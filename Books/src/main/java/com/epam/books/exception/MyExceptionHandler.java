package com.epam.books.exception;

import com.epam.books.data.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleQuestionNotFoundException(BookNotFoundException questionNotFoundException, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError(questionNotFoundException.getMessage());
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
        exceptionResponse.setTimestamp(new Date().toString());
        exceptionResponse.setPath(webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = BookNotSavedException.class)
    public ResponseEntity<ExceptionResponse> handleBookNotSavedException(BookNotSavedException bookNotSavedException, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError(bookNotSavedException.getMessage());
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
        exceptionResponse.setTimestamp(new Date().toString());
        exceptionResponse.setPath(webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BookNotRemovedException.class)
    public ResponseEntity<ExceptionResponse> handleBookNotRemovedException(BookNotRemovedException bookNotRemovedException, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError(bookNotRemovedException.getMessage());
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
        exceptionResponse.setTimestamp(new Date().toString());
        exceptionResponse.setPath(webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest req) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));

        ExceptionResponse exRes = new ExceptionResponse();
        exRes.setError(errors.toString());
        exRes.setStatus(HttpStatus.BAD_REQUEST.name());
        exRes.setTimestamp(new Date().toString());
        exRes.setPath(req.getDescription(false));

        return new ResponseEntity<>(exRes, HttpStatus.BAD_REQUEST);
    }

}
