package com.example.taskManagementSystem.exceptions;

public class InvalidRequestParameterException extends TaskManagementServiceException {

    public InvalidRequestParameterException(String message, String additionalProp) {
        super(message, additionalProp);
    }

    public InvalidRequestParameterException(String message) {
        super(message);
    }
}
