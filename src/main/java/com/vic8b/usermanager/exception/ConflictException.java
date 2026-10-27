package com.vic8b.usermanager.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends UserManagerException {
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
