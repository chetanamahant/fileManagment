package com.example.fileManagment.demo.fileManagment.exception;


public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String message) {
        super(message);
    }
}
