package com.app.msuser.controller;

import com.app.msuser.model.response.ApiError;
import com.app.msuser.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class ErrorController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handle (NotFoundException ex){
        return new ApiError(ex.getCode(), ex.getMessage());
    }
}
