package com.hackathon.jcs.global.base;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {

    HttpStatus httpStatus();

    String errorMessage();
}

