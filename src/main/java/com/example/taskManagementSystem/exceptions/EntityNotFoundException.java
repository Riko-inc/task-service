package com.example.taskManagementSystem.exceptions;

public class EntityNotFoundException extends TaskManagementServiceException {

    public EntityNotFoundException(String message, String additionalProp) {
        super(message, additionalProp);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
