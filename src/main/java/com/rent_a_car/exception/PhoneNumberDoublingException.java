package com.rent_a_car.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PhoneNumberDoublingException extends SQLIntegrityConstraintViolationException {
    public PhoneNumberDoublingException(String ex) {
        super(ex);
    }
}
