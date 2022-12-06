package com.epam.users.exception;

import com.epam.users.data.ExceptionResponse;
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

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleQuestionNotFoundException(UserNotFoundException questionNotFoundException, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError(questionNotFoundException.getMessage());
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
        exceptionResponse.setTimestamp(new Date().toString());
        exceptionResponse.setPath(webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = UserNotSavedException.class)
    public ResponseEntity<ExceptionResponse> handleBookNotSavedException(UserNotSavedException userNotSavedException, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError(userNotSavedException.getMessage());
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
        exceptionResponse.setTimestamp(new Date().toString());
        exceptionResponse.setPath(webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotRemovedException.class)
    public ResponseEntity<ExceptionResponse> handleBookNotRemovedException(UserNotRemovedException userNotRemovedException, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError(userNotRemovedException.getMessage());
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
