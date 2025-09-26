package org.example.controller;

import org.example.domain.dto.ErrorResponse;
import org.example.domain.exception.InsufficientLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({InsufficientLimitException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleInsufficientLimit(InsufficientLimitException e) {
        return new ErrorResponse(String.format("Списание на сумму %.2f невозможно. Превышен лимит %.2f. Доступный лимит %.2f",
                e.getAmount(), e.getDailyLimit(), e.getAvailableLimit()));
    }
}
