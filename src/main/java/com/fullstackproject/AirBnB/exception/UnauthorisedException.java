package com.fullstackproject.AirBnB.exception;

public class UnauthorisedException extends RuntimeException{
    public UnauthorisedException(String message){
        super(message);
    }
}
