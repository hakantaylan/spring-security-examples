package com.buraktas.entity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "A user with specified name already exists!")
public class UsernameAlreadyExistException extends RuntimeException {

    public UsernameAlreadyExistException() {
        super("Username already exists.");
    }
}
