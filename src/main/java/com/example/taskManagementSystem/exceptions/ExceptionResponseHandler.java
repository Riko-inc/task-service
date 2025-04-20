package com.example.taskManagementSystem.exceptions;

import com.example.taskManagementSystem.domain.dto.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class ExceptionResponseHandler {

    @ExceptionHandler({TaskManagementServiceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAccessDeniedException(TaskManagementServiceException exception) {
        return ErrorResponse.builder()
                .id(UUID.randomUUID())
                .message(exception.getMessage())
                .params(exception.getParams())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                        fieldErrors.put(error.getField(), error.getDefaultMessage())
                );

        return ErrorResponse.builder()
                .id(UUID.randomUUID())
                .message("Request fields validation failed")
                .params(fieldErrors)
                .build();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ErrorResponse.builder()
                .id(UUID.randomUUID())
                .message(ex.getMostSpecificCause().getMessage())
                .params(Collections.emptyMap())
                .build();
    }
}
