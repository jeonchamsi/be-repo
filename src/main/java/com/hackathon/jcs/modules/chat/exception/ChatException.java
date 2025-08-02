package com.hackathon.jcs.modules.chat.exception;

import com.hackathon.jcs.global.base.BaseException;
import com.hackathon.jcs.global.base.BaseExceptionType;

public class ChatException extends BaseException {

    private final ChatExceptionType exceptionType;

    public ChatException(ChatExceptionType exceptionType) {
        super(exceptionType.errorMessage());
        this.exceptionType = exceptionType;
    }

    public ChatException(String message, ChatExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
