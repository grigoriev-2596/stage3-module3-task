package com.mjc.school.service.exceptions;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
