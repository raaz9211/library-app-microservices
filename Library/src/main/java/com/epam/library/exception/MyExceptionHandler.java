package com.epam.library.exception;

import com.epam.library.data.ExceptionResponse;
import feign.FeignException;
import org.springframework.http.HttpHeaders;
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
//
//    @ExceptionHandler(value = HttpClientErrorException.class)
//    public ResponseEntity<ExceptionResponse> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest req) {
//
//
//        ExceptionResponse exRes = new ExceptionResponse();
//        exRes.setError(ex.getMessage());
//        exRes.setStatus(HttpStatus.BAD_REQUEST.name());
//        exRes.setTimestamp(new Date().toString());
//        exRes.setPath(req.getDescription(false));
//
//        return new ResponseEntity<>(exRes, HttpStatus.BAD_REQUEST);
//    }


    @ExceptionHandler(value = FeignException.class)
    public ResponseEntity<ExceptionResponse> handleFeignException(FeignException ex, WebRequest req) {
//        HttpStatus status = HttpStatus.resolve(ex.status());
//        String body = ex.contentUTF8();
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Content-Type", "application/json");
//
//        return new ResponseEntity(body, httpHeaders, status);

        ExceptionResponse exRes = new ExceptionResponse();
        exRes.setError(ex.getMessage());
        exRes.setStatus(HttpStatus.BAD_REQUEST.name());
        exRes.setTimestamp(new Date().toString());
        exRes.setPath(req.getDescription(false));

        return new ResponseEntity<>(exRes, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = BookNotMappedWithUserException.class)
    public ResponseEntity<ExceptionResponse> handleBookNotMappedWithUserException(BookNotMappedWithUserException ex, WebRequest req) {

        ExceptionResponse exRes = new ExceptionResponse();
        exRes.setError(ex.getMessage());
        exRes.setStatus(HttpStatus.BAD_REQUEST.name());
        exRes.setTimestamp(new Date().toString());
        exRes.setPath(req.getDescription(false));

        return new ResponseEntity<>(exRes, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BookMappedWithUserException.class)
    public ResponseEntity<ExceptionResponse> handleBookMappedWithUserException(BookMappedWithUserException ex, WebRequest req) {

        ExceptionResponse exRes = new ExceptionResponse();
        exRes.setError(ex.getMessage());
        exRes.setStatus(HttpStatus.BAD_REQUEST.name());
        exRes.setTimestamp(new Date().toString());
        exRes.setPath(req.getDescription(false));

        return new ResponseEntity<>(exRes, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = BookIssueLimitexceedException.class)
    public ResponseEntity<ExceptionResponse> handleBookIssueLimitExceedException(BookIssueLimitexceedException ex, WebRequest req) {

        ExceptionResponse exRes = new ExceptionResponse();
        exRes.setError(ex.getMessage());
        exRes.setStatus(HttpStatus.BAD_REQUEST.name());
        exRes.setTimestamp(new Date().toString());
        exRes.setPath(req.getDescription(false));

        return new ResponseEntity<>(exRes, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = BookNotReleasedWithUserException.class)
    public ResponseEntity<ExceptionResponse> handleBookNotReleasedWithUserException(BookNotReleasedWithUserException ex, WebRequest req) {

        ExceptionResponse exRes = new ExceptionResponse();
        exRes.setError(ex.getMessage());
        exRes.setStatus(HttpStatus.BAD_REQUEST.name());
        exRes.setTimestamp(new Date().toString());
        exRes.setPath(req.getDescription(false));

        return new ResponseEntity<>(exRes, HttpStatus.BAD_REQUEST);
    }


}
