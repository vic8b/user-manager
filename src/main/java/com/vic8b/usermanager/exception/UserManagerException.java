package com.vic8b.usermanager.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserManagerException extends RuntimeException {
    private final HttpStatus status;

    public UserManagerException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
