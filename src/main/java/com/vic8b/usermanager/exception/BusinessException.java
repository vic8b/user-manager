package com.vic8b.usermanager.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends UserManagerException {
    public BusinessException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
