package com.rentacar.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, String> handleMethodArgNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> handledErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
            handledErrors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return handledErrors;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(EmailDoublingException.class)
    String handleEmailDoublingException(EmailDoublingException ex) {
        return "Account with this email exists!";
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(PhoneNumberDoublingException.class)
    String handlePhoneNumberDoublingException(PhoneNumberDoublingException ex) {
        return "Phone number already exists!";
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(PassportIdDoublingException.class)
    String handlePassportIdDoublingException(PassportIdDoublingException ex) {
        return "Passport ID exists! Report to the administration to help for that identity theft!";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    String handlerRecordNotFoundException(RecordNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CarNotFoundException.class)
    String handlerCarNotFoundException(CarNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ReservationNotFoundException.class)
    String handlerReservationNotFoundException(ReservationNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    String handlerRuntimeException(RuntimeException ex) {
        return ex.getMessage();
    }
}
