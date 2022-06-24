package pos.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import pos.model.form.ApiError;
import pos.service.ApiException;

import javax.servlet.http.HttpServletRequest;

//Handles the exceptions to apply HTTP status
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError HandleNoSuchElementException(ApiException exception, HttpServletRequest request) {
        return new ApiError(400,exception.getMessage(),request.getServletPath());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError HandleSuchElementException(ConstraintViolationException exception,HttpServletRequest request) {
        return new ApiError(400,"The entry is repeated",request.getServletPath());
    }
}
