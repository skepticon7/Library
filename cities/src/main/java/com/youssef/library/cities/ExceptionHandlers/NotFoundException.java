package com.youssef.library.cities.ExceptionHandlers;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String type) {
        super(type + " not found");
    }

}
