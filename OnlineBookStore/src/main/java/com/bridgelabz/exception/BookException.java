package com.bridgelabz.exception;


import org.springframework.http.HttpStatus;

public class BookException extends Exception {

    private String message;

    public BookException(String message, ExceptionType exceptionType) {
        super(message);
        this.type=exceptionType;
    }

    public enum ExceptionType {
        BOOKS_NOT_AVAILABLE,ALREADY_IN_WISHLIST
    }
    public ExceptionType type;

}
