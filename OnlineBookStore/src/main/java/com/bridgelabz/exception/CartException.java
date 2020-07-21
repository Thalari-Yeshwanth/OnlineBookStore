package com.bridgelabz.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CartException extends Exception{

    private String message;

    public enum ExceptionType {
        EMPTY_CART
    }
    public ExceptionType type;

}
