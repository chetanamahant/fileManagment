package com.example.fileManagment.demo.fileManagment.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message)
    {
        super(message);
    }
}
