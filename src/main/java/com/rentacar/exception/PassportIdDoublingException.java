package com.rentacar.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PassportIdDoublingException extends SQLIntegrityConstraintViolationException {
    public PassportIdDoublingException(String ex) {
        super(ex);
    }
}
