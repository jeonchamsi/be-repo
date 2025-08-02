package com.hackathon.jcs.modules.chat.exception;

import com.hackathon.jcs.global.base.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum ChatExceptionType implements BaseExceptionType {
    NOT_EXISTING_NPC_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 npc id 입니다")
    ;

    private final HttpStatus httpStatus;
    private final String errorMessage;

    ChatExceptionType(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }
}
