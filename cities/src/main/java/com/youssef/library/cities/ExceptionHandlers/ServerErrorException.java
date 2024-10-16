package com.youssef.library.cities.ExceptionHandlers;

public class ServerErrorException extends RuntimeException{
    public ServerErrorException(String message) {
        super(message);
    }
}
