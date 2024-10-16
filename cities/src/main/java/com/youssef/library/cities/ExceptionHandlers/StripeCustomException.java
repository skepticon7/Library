package com.youssef.library.cities.ExceptionHandlers;

public class StripeCustomException extends RuntimeException{
    public StripeCustomException (String message) {
        super(message);
    }
}
