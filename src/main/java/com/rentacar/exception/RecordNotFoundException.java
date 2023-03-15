package com.rentacar.exception;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecordNotFoundException extends NoSuchElementException {
    public RecordNotFoundException(String ex) {
        super(ex);
    }
}
