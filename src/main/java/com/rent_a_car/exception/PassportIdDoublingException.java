package com.rent_a_car.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PassportIdDoublingException extends SQLIntegrityConstraintViolationException {
    public PassportIdDoublingException(String ex) {
        super(ex);
    }
}
