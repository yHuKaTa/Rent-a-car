package com.rentacar.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class EmailDoublingException extends SQLIntegrityConstraintViolationException {
    public EmailDoublingException(String ex) {
        super(ex);
    }
}
