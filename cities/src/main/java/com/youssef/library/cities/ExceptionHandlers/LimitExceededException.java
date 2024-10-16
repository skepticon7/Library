package com.youssef.library.cities.ExceptionHandlers;

public class LimitExceededException extends RuntimeException{
    public LimitExceededException(String type){
        super("capacity exceeded in " + type);
    }
}
