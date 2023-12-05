package com.rentacar.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class PhoneNumberDoublingException extends SQLIntegrityConstraintViolationException {
    public PhoneNumberDoublingException(String ex) {
        super(ex);
    }
}
