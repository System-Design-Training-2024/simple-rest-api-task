package com.postgresql.fdemo.exception;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }
}
