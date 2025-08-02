package com.hackathon.jcs.global.exception;

import com.hackathon.jcs.global.base.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> BaseException(final BaseException e) {
        final String errorMessage = e.getMessage();
        return ResponseEntity.status(e.exceptionType().httpStatus()).body(new ExceptionResponse(errorMessage));
    }
}
