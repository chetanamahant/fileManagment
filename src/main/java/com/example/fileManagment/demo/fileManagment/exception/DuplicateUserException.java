package com.example.fileManagment.demo.fileManagment.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {

        super(message);
    }
}
